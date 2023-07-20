package com.simonamilosheska.exceptions.venue;

public class NotExistingVenueException extends RuntimeException{

  public NotExistingVenueException(int id) {
    super(String.format("Venue with id [%d] does not exist",id));
  }
}
