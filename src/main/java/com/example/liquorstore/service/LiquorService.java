package com.example.liquorstore.service;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.model.PageDto;
import com.example.liquorstore.repository.liquors.Liquor;
import com.example.liquorstore.repository.liquors.LiquorRepository;
import com.example.liquorstore.repository.users.EndUser;
import com.example.liquorstore.repository.users.EndUserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    //    Liquor newLiquor = new Liquor(liquorDto.getId(), liquorDto.getName(),
    // liquorDto.getProducer(), liquorDto.getAbv(), liquorDto.getStock(), endUser);
    Liquor newLiquor = modelMapper.map(liquorDto, Liquor.class);
    newLiquor.setLiquorCreator(endUser);
    Liquor savedLiquor = liquorRepository.save(newLiquor);
    return modelMapper.map(savedLiquor, LiquorDto.class);
  }

  public LiquorDto findById(UUID id) {
    Liquor liquor = findByIdOrThrow(id);
    return modelMapper.map(liquor, LiquorDto.class);
  }

  public void update(UUID id, LiquorDto newLiquorDto) {
    newLiquorDto.setId(id);
    Liquor liquor = findByIdOrThrow(id);
    modelMapper.map(newLiquorDto, liquor);
    liquorRepository.save(liquor);
  }

  public void partialUpdate(UUID id, LiquorDto newLiquorDto) {
    newLiquorDto.setId(id);
    Liquor liquor = findByIdOrThrow(id);
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
}
