package com.simonamilosheska.responses;

public class CityResponse {
  private int id;
  private String name;

  public CityResponse(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }


}
