package com.simonamilosheska.models;

public class City extends BaseTable {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public City(int id,String name) {
    this.id=id;
    this.name = name;
  }

  public City() {
  }
}
