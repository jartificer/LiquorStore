package com.example.liquorstore.api.controllers;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.model.PageDto;
import com.example.liquorstore.service.LiquorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/liquors")
public class LiquorController {

  private final LiquorService liquorService;

  @Autowired
  public LiquorController(LiquorService liquorService) {
    this.liquorService = liquorService;
  }

  @PostMapping
  public ResponseEntity<Object> createLiquor(@RequestBody LiquorDto newLiquorDto) {
    LiquorDto liquorDto = liquorService.save(newLiquorDto);
    return ResponseEntity.created(URI.create("/api/v1/liquors/" + liquorDto.getId())).build();
  }

  @GetMapping("{id}")
  public LiquorDto findLiquorById(@PathVariable("id") int liquorId) {
    return liquorService.findById(liquorId);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLiquorById(@PathVariable("id") int liquorId) {
    liquorService.deleteById(liquorId);
  }

  @GetMapping
  public PageDto<LiquorDto> getAllLiquors(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
    return liquorService.getAllLiquors(page, pageSize, sortBy);
  }

  @PutMapping()
  public ResponseEntity<Object> updateLiquor(@RequestBody LiquorDto newLiquorDto) {
    LiquorDto liquorDto = liquorService.update(newLiquorDto);
    return ResponseEntity.ok().build();
  }
}
