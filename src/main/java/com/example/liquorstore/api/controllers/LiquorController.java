package com.example.liquorstore.api.controllers;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.service.LiquorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/liquors")
@Slf4j
public class LiquorController {

  private final LiquorService liquorService;

  @Autowired
  public LiquorController(LiquorService liquorService) {
    this.liquorService = liquorService;
  }


  @GetMapping
  public List<LiquorDto> getAllLiquors(LiquorDto newLiquorDto) {
    return liquorService.findAll(newLiquorDto);
  }

  @PostMapping
  public void createLiquor(@RequestBody LiquorDto newLiquorDto) {
    LiquorDto liquorDto = liquorService.save(newLiquorDto);
  }

  @GetMapping("{id}")
  public LiquorDto findLiquorById(@PathVariable("id") int liquorId) {
    return liquorService.findById(liquorId);
  }

  @DeleteMapping("{id}")
  public void deleteLiquorById(@PathVariable("id") int liquorId) {
    liquorService.deleteById(liquorId);
  }
}
