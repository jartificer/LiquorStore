package com.example.liquorstore.model;


public class LiquorDto {
  protected Integer id;
  protected String name;
  protected String producer;
  protected Float abv;
  protected Integer stock;


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

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public Float getAbv() {
    return abv;
  }

  public void setAbv(Float abv) {
    this.abv = abv;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }
}
