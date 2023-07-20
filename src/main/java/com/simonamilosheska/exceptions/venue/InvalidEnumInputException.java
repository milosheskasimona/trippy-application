package com.simonamilosheska.exceptions.venue;

public class InvalidEnumInputException extends RuntimeException {

  public InvalidEnumInputException(String message) {
    super(String.format("Venue type [%s] is invalid", message));
  }
}
