package com.simonamilosheska.requests;

import javax.validation.constraints.Pattern;

public class VenueRequestUpdate {
  private String address;
  @Pattern(regexp = "\\d{3}-\\d{3}-\\d{3}|\\d{3}/\\d{3}/\\d{3}|\\d{3}/\\d{3}-\\d{3}|d{9}",
           message = "Invalid phone number")
  private String phoneNumber;
  @Pattern(regexp = "^(.+)@(\\S+)$", message = "Invalid venue email, must be with @")
  private String emailAddress;
  @Pattern(regexp = "(http(s)?://)?(www\\.)?[a-zA-Z0-9]+\\.[a-zA-Z]{2,}(/[a-zA-Z0-9#]+)?")
  private String websiteUrl;


  public VenueRequestUpdate(String address, String phoneNumber, String emailAddress, String websiteUrl) {
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.websiteUrl = websiteUrl;
  }

  public VenueRequestUpdate() {
  }

  public String getAddress() {
    return address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getWebsiteUrl() {
    return websiteUrl;
  }
}
