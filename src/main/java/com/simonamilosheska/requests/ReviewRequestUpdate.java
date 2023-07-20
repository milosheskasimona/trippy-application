package com.simonamilosheska.requests;

import javax.validation.constraints.Positive;

public class ReviewRequestUpdate {
  @Positive
  private int rating;
  private String reviewComment;

  public ReviewRequestUpdate(int rating, String reviewComment) {
    this.rating = rating;
    this.reviewComment = reviewComment;
  }

  public ReviewRequestUpdate() {
  }

  public int getRating() {
    return rating;
  }

  public String getReviewComment() {
    return reviewComment;
  }
}
