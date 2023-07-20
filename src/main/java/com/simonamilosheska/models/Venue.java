package com.simonamilosheska.models;

import com.simonamilosheska.models.enums.VenueTypeEnum;

public class Venue extends BaseTable {

  private String name;
  private String address;
  private int cityFk;
  private int creatorId;
  private VenueTypeEnum venueType;
  private String phoneNumber;
  private String emailAddress;
  private String websiteUrl;
  private int numberOfReviews;
  private double rating;

  public Venue(
    String name, String address, int cityFk, int creatorId, VenueTypeEnum venueType, String phoneNumber,
    String emailAddress, String websiteUrl) {
    this.name = name;
    this.address = address;
    this.cityFk = cityFk;
    this.creatorId = creatorId;
    this.venueType = venueType;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.websiteUrl = websiteUrl;
  }
  public Venue(
    int id,
    String name, String address, int cityFk, int creatorId, VenueTypeEnum venueType, String phoneNumber,
    String emailAddress, String websiteUrl) {
    this.id=id;
    this.name = name;
    this.address = address;
    this.cityFk = cityFk;
    this.creatorId = creatorId;
    this.venueType = venueType;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.websiteUrl = websiteUrl;
  }

  public Venue() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getCityFk() {
    return cityFk;
  }

  public void setCityFk(int cityFk) {
    this.cityFk = cityFk;
  }

  public int getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(int creatorId) {
    this.creatorId = creatorId;
  }

  public VenueTypeEnum getVenueType() {
    return venueType;
  }

  public void setVenueType(VenueTypeEnum venueType) {
    this.venueType = venueType;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getWebsiteUrl() {
    return websiteUrl;
  }

  public void setWebsiteUrl(String websiteUrl) {
    this.websiteUrl = websiteUrl;
  }

  public int getNumberOfReviews() {
    return numberOfReviews;
  }

  public void setNumberOfReviews(int numberOfReviews) {
    this.numberOfReviews = numberOfReviews;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }
}