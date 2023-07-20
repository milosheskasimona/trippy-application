package com.simonamilosheska.exceptions.city;

public class NotExistingCityException extends RuntimeException {

  public NotExistingCityException(int cityId) {
    super(String.format("City with [%d] does not exist", cityId));
  }
}
