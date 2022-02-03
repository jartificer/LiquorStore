package com.example.liquorstore.api.controllers;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.repository.liquors.Liquor;
import com.example.liquorstore.service.LiquorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/liquors")
@Slf4j
public class LiquorController {

  @GetMapping("/hello")
  public String greetBuyer() {
    log.trace("A TRACE Message");
    log.debug("A DEBUG Message");
    log.info("An INFO Message");
    log.warn("A WARN Message");
    log.error("An ERROR Message");
    return "Greetings!";
  }

//  @GetMapping
//  public List<LiquorDto> getAllLiquors() {
//      return liquorService.getAllLiquors();
//  }
  // cu model mapper

  @Autowired
  private LiquorService liquorService;

  @PostMapping(consumes = {"application/json"})
  public void createLiquor(@RequestBody LiquorDto newLiquorDto) {
    LiquorDto liquorDto = liquorService.save(newLiquorDto);
    //tot cu model mapper
  }

  @GetMapping("/{id}")
  public LiquorDto findLiquorById(@PathVariable("id") int liquorId) {
    return liquorService.findById(liquorId);
    // cu model mapper
  }


//    //controller = layer de prezentare, interfata cu exteriorul
//    // Dto - delegare la service
//    // atentie la separation of concerns
//    // service il salveaza in baza de date
//
//    //LiquorStore.available.add(liquor);
//    return ResponseEntity.ok(liquor);
//  }

//  @PostMapping("/")
//  public @ResponseBody ResponseEntity<String> post() {
//    return new ResponseEntity<String>("POST Response", HttpStatus.OK);
//  }

}
