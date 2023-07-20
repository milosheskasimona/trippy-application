package com.simonamilosheska.exceptions.user;

public class NotExistingEmailAddressException extends RuntimeException {

  public NotExistingEmailAddressException(String email) {
    super(String.format("User with email address [%s] that you provided not exist", email));
  }
}
