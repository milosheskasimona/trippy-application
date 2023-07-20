package com.simonamilosheska.requests;

import javax.validation.constraints.Pattern;

public class UserRequest {
  @Pattern(regexp = "[A-Za-z\\s]+", message = "Invalid user name must, not be null or contain numbers")
  private String name;
  private String username;
  @Pattern(regexp = "^(.+)@(\\S+)$", message = "Invalid user email, must be with @")
  private String emailAddress;
  private int cityFk;

  public UserRequest(String name, String username, String emailAddress, int cityFk) {
    this.name = name;
    this.username = username;
    this.emailAddress = emailAddress;
    this.cityFk = cityFk;
  }

  public UserRequest() {
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

  public int getCityFk() {
    return cityFk;
  }
}
