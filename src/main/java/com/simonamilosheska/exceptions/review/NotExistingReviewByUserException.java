package com.simonamilosheska.exceptions.review;

public class NotExistingReviewByUserException extends RuntimeException {

  public NotExistingReviewByUserException(int reviewId, int userId) {
    super(String.format("Review with id [%d] it is not from user with id [%d]", reviewId, userId));
  }
}
