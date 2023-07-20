package com.simonamilosheska.exceptions.user;

public class AlreadyExistUserException extends RuntimeException {

  public AlreadyExistUserException() {
    super("User already exist with provided email address or username");
  }
}
