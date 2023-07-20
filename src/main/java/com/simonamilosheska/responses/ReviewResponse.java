package com.simonamilosheska.responses;


public class ReviewResponse {
  private int id;
  private int creatorUserId;
  private int rating;
  private String reviewComment;
  private String createdOn;

  public ReviewResponse(int id, int creatorUserId, int rating, String reviewComment, String createdOn) {
    this.id = id;
    this.creatorUserId = creatorUserId;
    this.rating = rating;
    this.reviewComment = reviewComment;
    this.createdOn = createdOn;
  }

  public ReviewResponse() {
  }

  public int getId() {
    return id;
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

  public String getCreatedOn() {
    return createdOn;
  }
}
