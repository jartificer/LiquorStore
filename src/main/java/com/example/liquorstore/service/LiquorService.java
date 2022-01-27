package com.example.liquorstore.service;

import com.example.liquorstore.repository.liquors.LiquorRepository;
import org.springframework.stereotype.Service;

@Service
public class LiquorService {
  private final LiquorRepository liquorRepository;

  public LiquorService(LiquorRepository liquorRepository) {
    this.liquorRepository = liquorRepository;
  }

}
