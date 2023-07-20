package com.simonamilosheska.services;

import com.simonamilosheska.mappers.ReviewMapper;
import com.simonamilosheska.models.Review;
import com.simonamilosheska.repositories.ReviewRepository;
import com.simonamilosheska.requests.ReviewRequest;
import com.simonamilosheska.requests.ReviewRequestUpdate;
import com.simonamilosheska.responses.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

  private ReviewRepository reviewRepository;
  private ReviewMapper reviewMapper;
  private UserService userService;
  private VenueService venueService;

  @Autowired
  public ReviewService(
    ReviewRepository reviewRepository, ReviewMapper reviewMapper, UserService userService, VenueService venueService) {
    this.reviewRepository = reviewRepository;
    this.reviewMapper = reviewMapper;
    this.userService = userService;
    this.venueService = venueService;
  }

  public List<ReviewResponse> getReviewsByVenueId(int venueId) {
    venueService.getVenueById(venueId);
    return reviewMapper.getReviewResponse(reviewRepository.getReviewsByVenueId(venueId));
  }

  public ReviewResponse getReviewById(int reviewId) {

    return reviewMapper.getReviewResponse(reviewRepository.getReviewById(reviewId));
  }

  public Review createReview(ReviewRequest reviewRequest, int venueId) {
    venueService.getVenueById(venueId);
    return reviewRepository.createReview(reviewMapper.getReview(reviewRequest, venueId));
  }

  public ReviewResponse editReview(ReviewRequestUpdate reviewRequestUpdate, int reviewId, int userId) {
    userService.getUserById(userId);
    getReviewById(reviewId);
    reviewRepository.getReviewByIdAndUser(reviewId, userId);
    reviewRepository.editReview(reviewRequestUpdate, reviewId);
    return getReviewById(reviewId);
  }

  public ReviewResponse deleteReview(int reviewId, int userId) {
    userService.getUserById(userId);
    reviewRepository.getReviewByIdAndUser(reviewId, userId);
    reviewRepository.deleteReview(reviewId);
    return getReviewById(reviewId);
  }
}
