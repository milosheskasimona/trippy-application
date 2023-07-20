package com.simonamilosheska.responses;

import java.util.List;

public class UserResponse {
  private int id;
  private String name;
  private String username;
  private String emailAddress;
  private int cityName;
  private List<Integer> reviews;
  private String createdOn;

  public UserResponse(
    int id, String name, String username, String emailAddress, int cityName, List<Integer> reviews, String createdOn) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.emailAddress = emailAddress;
    this.cityName = cityName;
    this.reviews = reviews;
    this.createdOn=createdOn;
  }

  public UserResponse() {

  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public int getCityName() {
    return cityName;
  }

  public List<Integer> getReviews() {
    return reviews;
  }

  public String getCreatedOn() {
    return createdOn;
  }
}
