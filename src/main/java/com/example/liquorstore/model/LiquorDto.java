package com.example.liquorstore.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LiquorDto {
  private Integer id;
  private String name;
  private String producer;
  private Float abv;
  private Integer stock;
}
