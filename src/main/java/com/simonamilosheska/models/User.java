package com.simonamilosheska.models;

import java.time.LocalDate;
import java.util.List;

public class User extends BaseTable {

  private String name;
  private String username;
  private String emailAddress;
  private int cityFk;
  private List<Integer> reviews;

  public User(String name, String username, String emailAddress, int cityFk) {
    this.name = name;
    this.username = username;
    this.emailAddress = emailAddress;
    this.cityFk = cityFk;
  }
  public User(int id,String name, String username, String emailAddress, int cityFk) {
    this.id=id;
    this.name = name;
    this.username = username;
    this.emailAddress = emailAddress;
    this.cityFk = cityFk;
  }
  public User() {
  }

  public User(int id, String name, String username, String email, int cityFk, LocalDate date) {
    super();
    this.createdOn=date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public int getCityFk() {
    return cityFk;
  }

  public void setCityId(int cityFk) {
    this.cityFk = cityFk;
  }

  public List<Integer> getReviews() {
    return reviews;
  }

  public void setReviews(List<Integer> reviews) {
    this.reviews = reviews;
  }
}
