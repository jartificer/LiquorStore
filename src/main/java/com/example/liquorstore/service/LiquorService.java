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

@Service
public class LiquorService {
  private final LiquorRepository liquorRepository;
  private final ModelMapper modelMapper;
  private final ModelMapper modelMapperSkipNull;

  @Autowired
  public LiquorService(
      LiquorRepository liquorRepository, ModelMapper modelMapper, ModelMapper modelMapperSkipNull) {
    this.liquorRepository = liquorRepository;
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

  public LiquorDto create(LiquorDto liquorDto) {
    Liquor newLiquor = modelMapper.map(liquorDto, Liquor.class);
    Liquor savedLiquor = liquorRepository.save(newLiquor);
    return modelMapper.map(savedLiquor, LiquorDto.class);
  }

  public LiquorDto findById(int id) {
    Liquor liquor = findByIdOrThrow(id);
    return modelMapper.map(liquor, LiquorDto.class);
  }

  public void update(int id, LiquorDto newLiquorDto) {
    newLiquorDto.setId(id);
    Liquor liquor = findByIdOrThrow(id);
    modelMapper.map(newLiquorDto, liquor);
    liquorRepository.save(liquor);
  }

  public void partialUpdate(int id, LiquorDto newLiquorDto) {
    newLiquorDto.setId(id);
    Liquor liquor = findByIdOrThrow(id);
    modelMapperSkipNull.map(newLiquorDto, liquor);
    liquorRepository.save(liquor);
  }

  public void deleteById(int id) throws ResourceNotFoundException {
    try {
      liquorRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("liquor not found");
    }
  }

  private Liquor findByIdOrThrow(int id) {
    return liquorRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("liquor not found"));
  }
}
