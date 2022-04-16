package dev.jartificer.liquorstore.api.controllers;

import dev.jartificer.liquorstore.model.LiquorDto;
import dev.jartificer.liquorstore.model.PageDto;
import dev.jartificer.liquorstore.service.LiquorService;
import dev.jartificer.liquorstore.service.UserDetailsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;


@RestController
@CrossOrigin()
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
  @Secured({"ROLE_READER", "ROLE_WRITER", "ROLE_ADMIN"})
  public LiquorDto findLiquorById(@PathVariable("id") UUID liquorId) {
    return liquorService.findById(liquorId);
  }

  @GetMapping
  @Secured({"ROLE_READER", "ROLE_WRITER", "ROLE_ADMIN"})
//  @PreAuthorize("hasAnyRole('reader','writer','admin')")
  public PageDto<LiquorDto> getAllLiquors(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
    return liquorService.getAllLiquors(page, pageSize, sortBy);
  }

  @PostMapping
  @Secured({"ROLE_WRITER", "ROLE_ADMIN"})
  public ResponseEntity<Object> createLiquor(@RequestBody LiquorDto newLiquorDto) {

    String username = userDetailsProvider.getUserDetails().getUsername();
    LiquorDto liquorDto = liquorService.create(newLiquorDto, username);

    return ResponseEntity.created(URI.create("/api/v1/liquors/" + liquorDto.getId())).build();
  }

  @PutMapping("{id}")
  @Secured({"ROLE_WRITER", "ROLE_ADMIN"})
  public ResponseEntity<Object> updateLiquor(
      @PathVariable("id") UUID id, @RequestBody LiquorDto newLiquorDto) {
    UserDetails userDetails = userDetailsProvider.getUserDetails();
    liquorService.update(id, newLiquorDto, userDetails);
    return ResponseEntity.ok().location(URI.create("/api/v1/liquors/" + id)).build();
  }

  @PatchMapping("{id}")
  @Secured({"ROLE_WRITER", "ROLE_ADMIN"})
  public ResponseEntity<LiquorDto> partialUpdateLiquor(
      @PathVariable UUID id, @RequestBody LiquorDto newLiquorDto) {
    UserDetails userDetails = userDetailsProvider.getUserDetails();
    liquorService.partialUpdate(id, newLiquorDto, userDetails);
    return ResponseEntity.ok().location(URI.create("/api/v1/liquors/" + id)).build();
  }

  @DeleteMapping("{id}")
  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLiquorById(@PathVariable("id") UUID liquorId) {
    liquorService.deleteById(liquorId);
  }
}
