package com.example.liquorstore.service;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.repository.liquors.Liquor;
import com.example.liquorstore.repository.liquors.LiquorRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;



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
    liquorRepository.save(newLiquor);
    return liquorDto;
  }

  public LiquorDto findById(int id) {
    Optional<Liquor> liquor = liquorRepository.findById(id);
    return modelMapper.map(
        liquor.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "liquor not found")), LiquorDto.class);
  }

  public void deleteById(int id) {
    liquorRepository.deleteById(id);
  }
}
