package com.simonamilosheska.mappers;

import com.simonamilosheska.models.City;
import com.simonamilosheska.models.Review;
import com.simonamilosheska.requests.ReviewRequest;
import com.simonamilosheska.responses.ReviewResponse;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.simonamilosheska.constants.ReviewConstants.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewMapperTest {
  @Mock
  private ResultSet resultSet;

  @InjectMocks
  private ReviewMapper reviewMapper;

  @Test
  public void testGetReviewsFromResultSet() throws SQLException {
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getInt(anyInt())).thenReturn(ID);
    when(resultSet.getInt(anyInt())).thenReturn(VENUE_ID);
    when(resultSet.getInt(anyInt())).thenReturn(USER_ID);
    when(resultSet.getInt(anyInt())).thenReturn(RATING);
    when(resultSet.getString(anyInt())).thenReturn(COMMENT);
    when(resultSet.getDate(anyInt())).thenReturn(Date.valueOf(LocalDate.now()));
    when(resultSet.getDate(anyInt())).thenReturn(Date.valueOf(LocalDate.now()));


    List<Review> reviewList = reviewMapper.getReviewsFromResultSet(resultSet);

    assertEquals(1, reviewList.size());
    assertEquals(COMMENT, reviewList.get(0).getReviewComment());
  }

  @Test(expected = RuntimeException.class)
  public void testGetReviewsFromResultSet_exception() throws SQLException {
    when(resultSet.next()).thenThrow(new SQLException());

    reviewMapper.getReviewsFromResultSet(resultSet);
  }
  @Test
  public void testGetReview(){
    ReviewRequest reviewRequest=new ReviewRequest(USER_ID,RATING,COMMENT);
    Review review=reviewMapper.getReview(reviewRequest,VENUE_ID);

    assertNotNull(review);
    assertEquals(reviewRequest.getReviewComment(),review.getReviewComment());
  }

  @Test
  public void testGetReviewResponse(){
    Review review=new Review(ID,VENUE_ID,USER_ID,RATING,COMMENT,LocalDate.parse(CREATED_ON));

    ReviewResponse reviewResponse = reviewMapper.getReviewResponse(review);

    assertEquals(review.getId(),reviewResponse.getId());
    assertEquals(review.getReviewComment(),review.getReviewComment());
    assertNotNull(reviewResponse);

  }

  @Test
  public void testGetReviewsResponse(){
    List<Review> list = Collections.singletonList(new Review(ID,VENUE_ID,USER_ID,RATING,COMMENT,LocalDate.parse(CREATED_ON)));

    List<ReviewResponse> actual = reviewMapper.getReviewResponse(list);

    assertEquals(list.size(), actual.size());
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i).getId(), actual.get(i).getId());
    }


  }




}