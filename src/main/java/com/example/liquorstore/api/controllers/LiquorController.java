package com.example.liquorstore.api.controllers;

import com.example.liquorstore.model.LiquorDto;
import com.example.liquorstore.repository.LiquorStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
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

  @GetMapping("/get")
  public ResponseEntity<List<LiquorDto>> getAvailableLiquors() {
    return ResponseEntity.ok(LiquorStore.available);
  }

  @PostMapping
  public @ResponseBody ResponseEntity<String> post() {
    return new ResponseEntity<String>("POST Response", HttpStatus.OK);
  }

//  @PostMapping
//  
//    public ResponseEntity<LiquorDTO> addLiquor(@RequestBody LiquorDTO liquor)
//    {
//      if (liquor == null && liquor.getName() == null && liquor.getProducer() == null)
//      {
//        return ResponseEntity.badRequest().body(liquor);
//      }
//      LiquorStore.available.add(liquor);
//
//      return ResponseEntity.ok(liquor);
//    }
//
//    @PutMapping("/{name}")
//    
//    public void updateLiquor(@PathVariable(name = "name") String name, @RequestBody LiquorDTO updatedLiquor)
//    {
//
//      List<LiquorDTO> available = LiquorStore.available;
//      for (int i = 0; i < available.size(); i++)
//      {
//        LiquorDTO liquor = available.get(i);
//        if (liquor.getName().equals(name))
//        {
//          available.remove(i);
//          available.add(updatedLiquor);
//          break;
//        }
//      }
//    }
//
//    @DeleteMapping("/{name}")
//    public void deleteLiquor(@PathVariable(name = "name") String name)
//    {
//      List<LiquorDTO> available = LiquorStore.available;
//      for (int i = 0; i < available.size(); i++)
//      {
//        LiquorDTO liquor = available.get(i);
//        if (liquor.getName().equals(name))
//        {
//          available.remove(i);
//          break;
//        }
//      }
//  }


}
