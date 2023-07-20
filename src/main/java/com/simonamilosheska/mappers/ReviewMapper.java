package com.simonamilosheska.mappers;

import com.simonamilosheska.models.Review;
import com.simonamilosheska.requests.ReviewRequest;
import com.simonamilosheska.responses.ReviewResponse;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

  public List<ReviewResponse> getReviewResponse(List<Review> reviews) {
    List<ReviewResponse> reviewResponses = new ArrayList<>();
    for (Review review : reviews) {
      reviewResponses.add(
        new ReviewResponse(review.getId(), review.getUserFk(), review.getRating(), review.getReviewComment(),
                           review.getCreatedOn().toString()));
    }
    return reviewResponses;
  }

  public ReviewResponse getReviewResponse(Review r) {
    return new ReviewResponse(r.getId(), r.getUserFk(), r.getRating(), r.getReviewComment(),
                              r.getCreatedOn().toString());
  }

  public List<Review> getReviewsFromResultSet(ResultSet resultSet) {
    List<Review> reviews = new ArrayList<>();
    try (resultSet) {
      while (resultSet.next()) {
        Review review = new Review();
        review.setId(resultSet.getInt(1));
        review.setVenueFk(resultSet.getInt(2));
        review.setUserFk(resultSet.getInt(3));
        review.setRating(resultSet.getInt(4));
        review.setReviewComment(resultSet.getString(5));
        review.setCreatedOn(resultSet.getDate(6).toLocalDate());
        Date deletedOn = resultSet.getDate(7);
        if (deletedOn != null) {
          review.setDeletedOn(deletedOn.toLocalDate());
        }

        reviews.add(review);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return reviews;
  }

  public Review getReview(ReviewRequest reviewRequest, int venueId) {
    return new Review(venueId, reviewRequest.getCreatorUserId(), reviewRequest.getRating(),
                      reviewRequest.getReviewComment());
  }
}
