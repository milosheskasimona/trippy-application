package com.simonamilosheska.exceptions.user;

public class NotExistingUsernameException extends RuntimeException {

  public NotExistingUsernameException(String username) {
    super(String.format("User with username [%s] that you provided not exist", username));
  }
}
