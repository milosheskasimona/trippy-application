package com.simonamilosheska.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simonamilosheska.models.Review;
import com.simonamilosheska.requests.ReviewRequest;
import com.simonamilosheska.responses.ReviewResponse;
import com.simonamilosheska.services.ReviewService;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.simonamilosheska.constants.ReviewConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {



  private MockMvc mockMvc;
  private Review review;
  private ReviewResponse reviewResponse;
  private ReviewRequest reviewRequest;

  @Mock
  private ReviewService reviewService;

  @InjectMocks
  private ReviewController reviewController;

  @Before
  public void setup() {
    review = new Review(ID, VENUE_ID, USER_ID, RATING, COMMENT);
    reviewResponse = new ReviewResponse(ID, USER_ID, RATING, COMMENT, CREATED_ON);
    reviewRequest = new ReviewRequest(USER_ID, RATING, COMMENT);
    mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
  }

  @Test
  public void testGetReviewsByVenueId() throws Exception {
    when(reviewService.getReviewsByVenueId(VENUE_ID)).thenReturn(Collections.singletonList(reviewResponse));
    mockMvc.perform(get("/venues/" + VENUE_ID + "/reviews"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].creatorUserId").value(USER_ID))
           .andExpect(jsonPath("$[0].rating").value(RATING))
           .andExpect(jsonPath("$[0].reviewComment").value(COMMENT))
           .andExpect(jsonPath("$[0].createdOn").value(CREATED_ON));

    verify(reviewService, times(1)).getReviewsByVenueId(VENUE_ID);
  }

  @Test
  public void testCreateReview() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(reviewRequest);
    when(reviewService.createReview(any(), eq(VENUE_ID))).thenReturn(review);
    mockMvc.perform(post("/venues/" + VENUE_ID + "/reviews")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/reviews/" + ID));
  }

  @Test
  public void testEditReview() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(reviewRequest);

    when(reviewService.editReview(any(), eq(ID), eq(USER_ID))).thenReturn(reviewResponse);

    mockMvc.perform(put("/users/" + USER_ID + "/reviews/" + ID)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isNoContent());
  }

  @Test
  public void testDeleteReview() throws Exception {

    when(reviewService.deleteReview(ID, USER_ID)).thenReturn(reviewResponse);
    mockMvc.perform(delete("/users/" + USER_ID + "/reviews/" + ID))
           .andExpect(status().isNoContent());
  }
}