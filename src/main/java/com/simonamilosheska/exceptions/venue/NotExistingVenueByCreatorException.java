package com.simonamilosheska.exceptions.venue;

public class NotExistingVenueByCreatorException extends RuntimeException{

  public NotExistingVenueByCreatorException(int venueId, int creatorId) {
    super(String.format("Venue with id [%d] it is not from creator with id [%d]",venueId,creatorId));
  }
}
