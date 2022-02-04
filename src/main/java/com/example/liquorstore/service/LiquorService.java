package com.example.liquorstore.service;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.repository.liquors.Liquor;
import com.example.liquorstore.repository.liquors.LiquorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LiquorService {
  private final LiquorRepository liquorRepository;

  //  public List<LiquorDto> getAllLiquors(){
  //    return liquorRepository.findAll();
  // de folosit model mapper
  //  }

  public LiquorService(LiquorRepository liquorRepository) {
    this.liquorRepository = liquorRepository;
  }

  public LiquorDto save(LiquorDto liquorDto) {
    ModelMapper modelMapper = new ModelMapper();
    Liquor newLiquor = modelMapper.map(liquorDto, Liquor.class);
    liquorRepository.save(newLiquor);
    return liquorDto;
  }

  public LiquorDto findById(int id) {
    Optional<Liquor> liquor = liquorRepository.findById(id);
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(liquor.orElseThrow(() -> new RuntimeException("liquor not found")), LiquorDto.class);
  }
}
