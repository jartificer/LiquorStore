package com.example.liquorstore.api.controllers;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.model.PageDto;
import com.example.liquorstore.service.LiquorService;
import com.example.liquorstore.service.UserDetailsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/liquors")
public class LiquorController {

  private final LiquorService liquorService;
  private final UserDetailsProvider userDetailsProvider;

  @Autowired
  public LiquorController(LiquorService liquorService, UserDetailsProvider userDetailsProvider) {
    this.liquorService = liquorService;
    this.userDetailsProvider = userDetailsProvider;
  }

  @GetMapping("{id}")
  @PreAuthorize("hasAnyRole('reader','writer','admin')")
  public LiquorDto findLiquorById(@PathVariable("id") UUID liquorId) {
    return liquorService.findById(liquorId);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('reader','writer','admin')")
  public PageDto<LiquorDto> getAllLiquors(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
    return liquorService.getAllLiquors(page, pageSize, sortBy);
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('writer','admin')")
  public ResponseEntity<Object> createLiquor(@RequestBody LiquorDto newLiquorDto) {

    String username = userDetailsProvider.getUserDetails().getUsername();
    LiquorDto liquorDto = liquorService.create(newLiquorDto, username);

    return ResponseEntity.created(URI.create("/api/v1/liquors/" + liquorDto.getId())).build();
  }

  @PutMapping("{id}")
  @PreAuthorize("hasAnyRole('writer','admin')")
  public ResponseEntity<Object> updateLiquor(
      @PathVariable("id") UUID id, @RequestBody LiquorDto newLiquorDto) {
    liquorService.update(id, newLiquorDto);
    return ResponseEntity.ok().location(URI.create("/api/v1/liquors/" + id)).build();
  }

  @PatchMapping("{id}")
  @PreAuthorize("hasAnyRole('writer','admin')")
  public ResponseEntity<LiquorDto> partialUpdateLiquor(
      @PathVariable UUID id, @RequestBody LiquorDto newLiquorDto) {
    liquorService.partialUpdate(id, newLiquorDto);
    return ResponseEntity.ok().location(URI.create("/api/v1/liquors/" + id)).build();
  }

  @DeleteMapping("{id}")
  @PreAuthorize("hasAnyRole('writer','admin')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLiquorById(@PathVariable("id") UUID liquorId) {
    liquorService.deleteById(liquorId);
  }
}
