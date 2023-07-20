package com.simonamilosheska.exceptions.user;

public class NotExistingUserException extends RuntimeException {

  public NotExistingUserException(int id) {
    super(String.format("User with id [%d] that you provided not exist", id));
  }
}
