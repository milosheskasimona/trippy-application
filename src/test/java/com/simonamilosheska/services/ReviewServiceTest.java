package com.simonamilosheska.services;

import com.simonamilosheska.mappers.ReviewMapper;
import com.simonamilosheska.mappers.UserMapper;
import com.simonamilosheska.models.Review;
import com.simonamilosheska.models.User;
import com.simonamilosheska.models.Venue;
import com.simonamilosheska.repositories.ReviewRepository;
import com.simonamilosheska.repositories.UserRepository;
import com.simonamilosheska.requests.ReviewRequest;
import com.simonamilosheska.requests.ReviewRequestUpdate;
import com.simonamilosheska.requests.UserRequest;
import com.simonamilosheska.responses.ReviewResponse;
import com.simonamilosheska.responses.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.simonamilosheska.constants.ReviewConstants.*;

;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {

  @Mock
  private ReviewRepository reviewRepository;
  @Mock
  private ReviewMapper reviewMapper;
  @Mock
  private VenueService venueService;
  @Mock
  private UserService userService;

  @InjectMocks
  private ReviewService reviewService;

  @Test
  public void testGetReviewsByVenueId() {
    when(venueService.getVenueById(VENUE_ID)).thenReturn(new Venue());

    List<Review> reviews = Collections.singletonList(new Review(ID, VENUE_ID, USER_ID, RATING, COMMENT));
    when(reviewRepository.getReviewsByVenueId(VENUE_ID)).thenReturn(reviews);

    List<ReviewResponse> reviewResponses =
      Collections.singletonList(new ReviewResponse(ID, USER_ID, RATING, COMMENT, CREATED_ON));
    when(reviewMapper.getReviewResponse(reviews)).thenReturn(reviewResponses);

    List<ReviewResponse> result = reviewService.getReviewsByVenueId(VENUE_ID);

    assertEquals(reviewResponses.get(0).getId(), result.get(0).getId());
    verify(reviewRepository, times(1)).getReviewsByVenueId(VENUE_ID);
  }

  @Test
  public void testGetReviewById() {
    Review review = new Review(ID, VENUE_ID, USER_ID, RATING, COMMENT);
    when(reviewRepository.getReviewById(ID)).thenReturn(review);

    ReviewResponse reviewResponse = new ReviewResponse(ID, USER_ID, RATING, COMMENT, CREATED_ON);
    when(reviewMapper.getReviewResponse(review)).thenReturn(reviewResponse);

    ReviewResponse resultResponse = reviewService.getReviewById(ID);

    assertEquals(review.getId(), resultResponse.getId());
    verify(reviewRepository, times(1)).getReviewById(ID);
  }
  @Test
  public void testCreateReview() {
    when(venueService.getVenueById(VENUE_ID)).thenReturn(new Venue());
    Review review = new Review(ID, VENUE_ID, USER_ID, RATING, COMMENT);
    when(reviewRepository.createReview(review)).thenReturn(review);

    ReviewRequest reviewRequest = new ReviewRequest(USER_ID,RATING,COMMENT);
    when(reviewMapper.getReview(reviewRequest,VENUE_ID)).thenReturn(review);

    Review resultReview = reviewService.createReview(reviewRequest,VENUE_ID);

    assertEquals(review.getId(), resultReview.getId());
    verify(reviewRepository, times(1)).createReview(review);
  }

  @Test
  public void testEditReview() {
    when(userService.getUserById(USER_ID)).thenReturn(new UserResponse());
    ReviewRequestUpdate reviewRequestUpdate = new ReviewRequestUpdate( RATING, COMMENT);

    ReviewResponse reviewResponse = new ReviewResponse(ID, USER_ID, RATING, COMMENT, CREATED_ON);
    when(reviewService.getReviewById(ID)).thenReturn(reviewResponse);

    Review review = new Review(ID, VENUE_ID, USER_ID, RATING, COMMENT);
    when(reviewRepository.getReviewByIdAndUser(ID,USER_ID)).thenReturn(review);

    ReviewResponse resultReviewResponse=reviewService.editReview(reviewRequestUpdate,ID,USER_ID);
    verify(reviewRepository, times(1)).editReview(reviewRequestUpdate,ID);
    assertEquals(reviewRequestUpdate.getReviewComment(), resultReviewResponse.getReviewComment());
  }

  @Test
  public void testDeleteReview() {
    when(userService.getUserById(USER_ID)).thenReturn(new UserResponse());


    Review review = new Review(ID, VENUE_ID, USER_ID, RATING, COMMENT);
    when(reviewRepository.getReviewByIdAndUser(ID,USER_ID)).thenReturn(review);

    ReviewResponse resultReviewResponse=reviewService.deleteReview(ID,USER_ID);
    verify(reviewRepository, times(1)).deleteReview(ID);
  }


}