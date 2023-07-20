package com.simonamilosheska.controllers;

import com.simonamilosheska.models.Review;
import com.simonamilosheska.requests.ReviewRequest;
import com.simonamilosheska.requests.ReviewRequestUpdate;
import com.simonamilosheska.responses.ReviewResponse;
import com.simonamilosheska.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
public class ReviewController {

  private final ReviewService reviewService;

  @Autowired
  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/venues/{venueId}/reviews")
  public ResponseEntity<List<ReviewResponse>> getReviewsByVenueId(@PathVariable int venueId) {
    List<ReviewResponse> reviewResponse = reviewService.getReviewsByVenueId(venueId);
    return ResponseEntity.ok(reviewResponse);
  }

  @PostMapping("/venues/{venueId}/reviews")
  public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewRequest reviewRequest, @PathVariable int venueId) {
    Review review = reviewService.createReview(reviewRequest, venueId);

    URI location = UriComponentsBuilder.fromUriString("/reviews/{id}")
                                       .buildAndExpand(review.getId())
                                       .toUri();
    return ResponseEntity.created(location).build();
  }

  @PutMapping("/users/{userId}/reviews/{reviewId}")
  public ResponseEntity<ReviewResponse> editReview(
    @RequestBody @Valid ReviewRequestUpdate reviewRequestUpdate, @PathVariable
    int reviewId, @PathVariable int userId) {
    reviewService.editReview(reviewRequestUpdate, reviewId, userId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("users/{userId}/reviews/{reviewId}")
  public ResponseEntity<ReviewResponse> deleteReview(@PathVariable int userId, @PathVariable int reviewId) {
    reviewService.deleteReview(reviewId, userId);
    return ResponseEntity.noContent().build();
  }


}
