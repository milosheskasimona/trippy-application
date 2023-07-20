package com.simonamilosheska.requests;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class VenueRequest {

  private String name;
  private String address;
  @Positive
  private int cityId;

  @Pattern(regexp = "(?i)(hotel|restaurant|bar)")
  private String venueType;
  @Pattern(regexp = "\\d{3}-\\d{3}-\\d{3}|\\d{3}/\\d{3}/\\d{3}|\\d{3}/\\d{3}-\\d{3}|d{9}",
           message = "Invalid phone number")
  private String phoneNumber;
  @Pattern(regexp = "^(.+)@(\\S+)$", message = "Invalid venue email, must be with @")
  private String emailAddress;
  @Pattern(regexp = "(http(s)?://)?(www\\.)?[a-zA-Z0-9]+\\.[a-zA-Z]{2,}(/[a-zA-Z0-9#]+)?")
  private String websiteUrl;

  public VenueRequest(
    String name, String address, int cityId, String venueType, String phoneNumber,
    String emailAddress, String websiteUrl) {
    this.name = name;
    this.address = address;
    this.cityId = cityId;
    this.venueType = venueType;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.websiteUrl = websiteUrl;
  }

  public VenueRequest() {
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public int getCityId() {
    return cityId;
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
    return websiteUrl;
  }
}
