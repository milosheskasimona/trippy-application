package com.simonamilosheska.exceptions.review;

public class NotExistingReviewException extends RuntimeException {

  public NotExistingReviewException(int id) {
    super(String.format("Review with id [%d] does not exist", id));
  }
}
