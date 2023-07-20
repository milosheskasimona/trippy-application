package com.simonamilosheska.responses;

public class VenueResponse {

  private int id;
  private String name;
  private String address;
  private String venueType;
  private String phoneNumber;
  private String emailAddress;
  private String webSiteUrl;
  private int numberOfReviews;
  private double rating;

  public VenueResponse(
    int id, String name, String address, String venueType, String phoneNumber, String emailAddress, String webSiteUrl,
    int numberOfReviews, double rating) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.venueType = venueType;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.webSiteUrl = webSiteUrl;
    this.numberOfReviews = numberOfReviews;
    this.rating = rating;
  }

  public VenueResponse() {
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getVenueType() {
    return venueType;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getWebSiteUrl() {
    return webSiteUrl;
  }

  public int getNumberOfReviews() {
    return numberOfReviews;
  }

  public double getRating() {
    return rating;
  }
}
