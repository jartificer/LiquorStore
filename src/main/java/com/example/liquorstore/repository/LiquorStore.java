package com.example.liquorstore.repository;

import com.example.liquorstore.model.LiquorDto;

import java.util.ArrayList;
import java.util.List;

public class LiquorStore {

  public static final List<LiquorDto> available =
      new ArrayList<LiquorDto>() {
        {
          add(new LiquorDto(1, "testliquor1", "testprod1", 5F, 12));
          add(new LiquorDto(2, "testliquor2", "testprod2", 5F, 20));
        }
      };
}
