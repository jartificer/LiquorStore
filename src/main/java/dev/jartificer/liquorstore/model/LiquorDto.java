package dev.jartificer.liquorstore.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class LiquorDto {
  private UUID id;
  private String name;
  private String producer;
  private Float abv;
  private Integer stock;
}
