package com.simonamilosheska.exceptions.venue;

public class AlreadyExistVenueException extends RuntimeException{

  public AlreadyExistVenueException(String name, int cityId) {
    super(String.format("Venue with name [%s] and cityId [%d] already exist",name,cityId));
  }
}
