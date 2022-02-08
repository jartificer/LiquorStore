package com.example.liquorstore.service;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.repository.liquors.Liquor;
import com.example.liquorstore.repository.liquors.LiquorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiquorService {
  private final LiquorRepository liquorRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public LiquorService(LiquorRepository liquorRepository, ModelMapper modelMapper) {
    this.liquorRepository = liquorRepository;
    this.modelMapper = modelMapper;
  }

  public List<LiquorDto> findAll(LiquorDto liquorDto) {
    Liquor liquor = modelMapper.map(liquorDto, Liquor.class);
    List<Liquor> liquors = liquorRepository.findAll();
    return modelMapper.map(liquors, new TypeToken<List<LiquorDto>>() {}.getType());
  }

  public LiquorDto save(LiquorDto liquorDto) {
    Liquor newLiquor = modelMapper.map(liquorDto, Liquor.class);
    Liquor savedLiquor = liquorRepository.save(newLiquor);
    return modelMapper.map(savedLiquor, LiquorDto.class);
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
      throw new ResourceNotFoundException();
    }
  }
}
