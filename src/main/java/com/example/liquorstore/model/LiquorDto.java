package com.example.liquorstore.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LiquorDto {
  protected Integer id;
  protected String name;
  protected String producer;
  protected Float abv;
  protected Integer stock;

}
