package dev.jartificer.liquorstore.service;

import dev.jartificer.liquorstore.model.LiquorDto;
import dev.jartificer.liquorstore.model.PageDto;
import dev.jartificer.liquorstore.repository.liquors.Liquor;
import dev.jartificer.liquorstore.repository.liquors.LiquorRepository;
import dev.jartificer.liquorstore.repository.users.EndUser;
import dev.jartificer.liquorstore.repository.users.EndUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LiquorService {
  private final LiquorRepository liquorRepository;
  private final EndUserRepository endUserRepository;
  private final ModelMapper modelMapper;
  private final ModelMapper modelMapperSkipNull;

  @Autowired
  public LiquorService(
      LiquorRepository liquorRepository,
      EndUserRepository endUserRepository,
      ModelMapper modelMapper,
      ModelMapper modelMapperSkipNull) {
    this.liquorRepository = liquorRepository;
    this.endUserRepository = endUserRepository;
    this.modelMapper = modelMapper;
    this.modelMapperSkipNull = modelMapperSkipNull;
  }

  public PageDto<LiquorDto> getAllLiquors(Integer page, Integer pageSize, String sortBy) {
    Pageable pageRequest = PageRequest.of(page, pageSize, Sort.by(sortBy));
    Page<Liquor> pagedResult = liquorRepository.findAll(pageRequest);

    List<LiquorDto> result =
        modelMapper.map(pagedResult.getContent(), new TypeToken<List<LiquorDto>>() {}.getType());

    PageDto<LiquorDto> liquorPage = new PageDto<>();
    liquorPage.setPage(page);
    liquorPage.setPageSize(pageSize);
    liquorPage.setData(result);

    return liquorPage;
  }

  public LiquorDto create(LiquorDto liquorDto, String username) {

    EndUser endUser =
        endUserRepository
            .findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    Liquor newLiquor = modelMapper.map(liquorDto, Liquor.class);
    newLiquor.setLiquorCreator(endUser);
    Liquor savedLiquor = liquorRepository.save(newLiquor);
    return modelMapper.map(savedLiquor, LiquorDto.class);
  }

  public LiquorDto findById(UUID id) {
    Liquor liquor = findByIdOrThrow(id);
    return modelMapper.map(liquor, LiquorDto.class);
  }

  public void update(UUID id, LiquorDto newLiquorDto, UserDetails userDetails) {
    Liquor liquor = findByIdOrThrow(id);
    if (!isCreatorOrAdmin(liquor, userDetails)) {
      throw new AccessDeniedException("You must be the creator of this entry to update it");
    }
    newLiquorDto.setId(id);
    modelMapper.map(newLiquorDto, liquor);
    liquorRepository.save(liquor);
  }

  public void partialUpdate(UUID id, LiquorDto newLiquorDto, UserDetails userDetails) {
    Liquor liquor = findByIdOrThrow(id);
    if (!isCreatorOrAdmin(liquor, userDetails)) {
      throw new AccessDeniedException("You must be the creator of this entry to update it");
    }
    newLiquorDto.setId(id);
    modelMapperSkipNull.map(newLiquorDto, liquor);
    liquorRepository.save(liquor);
  }

  public void deleteById(UUID id) throws ResourceNotFoundException {
    try {
      liquorRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("liquor not found");
    }
  }

  private Liquor findByIdOrThrow(UUID id) {
    return liquorRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("liquor not found"));
  }

  Boolean isCreatorOrAdmin(Liquor liquor, UserDetails userDetails) {
    return liquor.getLiquorCreator().getEmail().equals(userDetails.getUsername())
        || userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch("admin"::equalsIgnoreCase);
  }
}
