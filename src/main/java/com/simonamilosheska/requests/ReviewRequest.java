package com.simonamilosheska.requests;

import javax.validation.constraints.Positive;

public class ReviewRequest {

  @Positive
  private int creatorUserId;
  @Positive
  private int rating;
  private String reviewComment;

  public ReviewRequest(int creatorUserId, int rating, String reviewComment) {
    this.creatorUserId = creatorUserId;
    this.rating = rating;
    this.reviewComment = reviewComment;
  }

  public ReviewRequest() {
  }

  public int getCreatorUserId() {
    return creatorUserId;
  }

  public int getRating() {
    return rating;
  }

  public String getReviewComment() {
    return reviewComment;
  }
}
