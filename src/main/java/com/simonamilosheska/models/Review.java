package com.simonamilosheska.models;

import java.time.LocalDate;

public class Review extends BaseTable {
  private int venueFk;
  private int userFk;
  private int rating;
  private String reviewComment;

  public Review(int venueFk, int userFk, int rating, String reviewComment) {
    this.venueFk = venueFk;
    this.userFk = userFk;
    this.rating = rating;
    this.reviewComment = reviewComment;
  }
  public Review(int id,int venueFk, int userFk, int rating, String reviewComment) {
    this.id=id;
    this.venueFk = venueFk;
    this.userFk = userFk;
    this.rating = rating;
    this.reviewComment = reviewComment;
  }
  public Review(int id, int venueFk, int userFk, int rating, String reviewComment, LocalDate createdOn) {
    this.id=id;
    this.venueFk = venueFk;
    this.userFk = userFk;
    this.rating = rating;
    this.reviewComment = reviewComment;
    this.createdOn=createdOn;
  }


  public Review() {
  }

  public int getVenueFk() {
    return venueFk;
  }

  public void setVenueFk(int venueFk) {
    this.venueFk = venueFk;
  }

  public int getUserFk() {
    return userFk;
  }

  public void setUserFk(int userFk) {
    this.userFk = userFk;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getReviewComment() {
    return reviewComment;
  }

  public void setReviewComment(String reviewComment) {
    this.reviewComment = reviewComment;
  }
}
