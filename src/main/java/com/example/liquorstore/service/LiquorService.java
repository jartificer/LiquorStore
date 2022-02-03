package com.example.liquorstore.service;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.repository.liquors.Liquor;
import com.example.liquorstore.repository.liquors.LiquorRepository;
import liquibase.pro.packaged.id;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class LiquorService {
  private final LiquorRepository liquorRepository;

  //  public List<LiquorDto> getAllLiquors(){
  //    return liquorRepository.findAll();
  // de folosit --object-- (de fapt model) mapper
  //  }

  public LiquorService(LiquorRepository liquorRepository) {
    this.liquorRepository = liquorRepository;
  }

  public LiquorDto save(LiquorDto liquorDto) {
    Liquor newLiquor = new Liquor();
    newLiquor.setName(liquorDto.getName());
    newLiquor.setId(liquorDto.getId());
    newLiquor.setProducer(liquorDto.getProducer());
    newLiquor.setAbv(liquorDto.getAbv());
    newLiquor.setStock(liquorDto.getStock());
    liquorRepository.save(newLiquor);
    return liquorDto;
  }

  public LiquorDto findById(int id) {
    return liquorRepository
        .findById(id)
        .map(x -> new LiquorDto(x.getId(), x.getName(), x.getProducer(), x.getAbv(), x.getStock()))
        .orElseThrow(() -> new RuntimeException("liquor not found"));
  }
}
