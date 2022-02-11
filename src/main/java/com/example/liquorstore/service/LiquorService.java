package com.example.liquorstore.service;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.model.PageDto;
import com.example.liquorstore.repository.liquors.Liquor;
import com.example.liquorstore.repository.liquors.LiquorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
//test


@Service
public class LiquorService {
  private final LiquorRepository liquorRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public LiquorService(LiquorRepository liquorRepository, ModelMapper modelMapper) {
    this.liquorRepository = liquorRepository;
    this.modelMapper = modelMapper;
  }

  public LiquorDto save(LiquorDto liquorDto) {
    Liquor newLiquor = modelMapper.map(liquorDto, Liquor.class);
    Liquor savedLiquor = liquorRepository.save(newLiquor);
    return modelMapper.map(savedLiquor, LiquorDto.class);
  }

  public LiquorDto update(LiquorDto newLiquorDto) {

    Liquor newLiquor = modelMapper.map(newLiquorDto, Liquor.class);
    newLiquor = liquorRepository.save(newLiquor);
    return modelMapper.map(newLiquor, LiquorDto.class);
  }

  public LiquorDto findById(int id) {
    Optional<Liquor> liquor = liquorRepository.findById(id);
    return modelMapper.map(
        liquor.orElseThrow(() -> new ResourceNotFoundException("liquor not found")),
        LiquorDto.class);
  }

  public void deleteById(int id) throws ResourceNotFoundException {
    try {
      liquorRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("liquor not found");
    }
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
}
