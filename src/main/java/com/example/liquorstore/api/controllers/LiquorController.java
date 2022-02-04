package com.example.liquorstore.api.controllers;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.repository.liquors.Liquor;
import com.example.liquorstore.service.LiquorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @Autowired
  private LiquorService liquorService;

  @GetMapping("/")
  public List<Liquor> getAllLiquors() {
    return liquorService.findAll();
  }

  @PostMapping(consumes = {"application/json"})
  public void createLiquor(@RequestBody LiquorDto newLiquorDto) {
    LiquorDto liquorDto = liquorService.save(newLiquorDto);
  }

  @GetMapping("/{id}")
  public LiquorDto findLiquorById(@PathVariable("id") int liquorId) {
    return liquorService.findById(liquorId);
  }


//  @PostMapping("/")
//  public @ResponseBody ResponseEntity<String> post() {
//    return new ResponseEntity<String>("POST Response", HttpStatus.OK);
//  }

}
