package com.example.liquorstore.repository.liquors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Liquor {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;
  private String producer;
  private Float abv;
  private Integer stock;

  public Liquor() {}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProducer() {
    return producer;
  }

  public void setProducer(String name) {
    this.producer = producer;
  }

  public Float getAbv() {
    return abv;
  }

  public void setAbv() {
    this.abv = abv;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }



}
