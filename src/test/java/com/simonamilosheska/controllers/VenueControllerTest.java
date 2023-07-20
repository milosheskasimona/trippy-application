package com.simonamilosheska.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simonamilosheska.models.Venue;
import com.simonamilosheska.models.enums.VenueTypeEnum;
import com.simonamilosheska.requests.VenueRequest;
import com.simonamilosheska.requests.VenueRequestUpdate;
import com.simonamilosheska.responses.VenueResponse;
import com.simonamilosheska.services.VenueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.simonamilosheska.constants.VenueConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class VenueControllerTest {



  private MockMvc mockMvc;
  private Venue venue;
  private VenueResponse venueResponse;
  private VenueRequest venueRequest;
  private VenueRequestUpdate venueRequestUpdate;

  @Mock
  private VenueService venueService;

  @InjectMocks
  private VenueController venueController;

  @Before
  public void setup() {
    venue = new Venue(ID, NAME, ADDRESS, CITY, USER_ID, VenueTypeEnum.HOTEL, PHONE, EMAIL, WEB);
    venueResponse = new VenueResponse(ID, NAME, ADDRESS, TYPE, PHONE, EMAIL, WEB, NUMBER_REVIEWS, RATING);
    venueRequest = new VenueRequest(NAME, ADDRESS, CITY, TYPE, PHONE, EMAIL, WEB);
    venueRequestUpdate = new VenueRequestUpdate(ADDRESS, PHONE, EMAIL, WEB);
    mockMvc = MockMvcBuilders.standaloneSetup(venueController).build();
  }

  @Test
  public void testGetVenues() throws Exception {
    when(venueService.getAllVenues()).thenReturn(Collections.singletonList(venueResponse));
    mockMvc.perform(get("/venues"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].name").value(NAME))
           .andExpect(jsonPath("$[0].address").value(ADDRESS))
           .andExpect(jsonPath("$[0].venueType").value(TYPE))
           .andExpect(jsonPath("$[0].phoneNumber").value(PHONE))
           .andExpect(jsonPath("$[0].emailAddress").value(EMAIL))
           .andExpect(jsonPath("$[0].webSiteUrl").value(WEB))
           .andExpect(jsonPath("$[0].numberOfReviews").value(NUMBER_REVIEWS))
           .andExpect(jsonPath("$[0].rating").value(RATING));
  }

  @Test
  public void testCreateVenue() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(venueRequest);
    when(venueService.createVenue(any(), eq(USER_ID))).thenReturn(venue);
    mockMvc.perform(post("/users/" + USER_ID + "/venues")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/venues/" + ID));
  }

  @Test
  public void testEditReview() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(venueRequestUpdate);

    when(venueService.editVenue(any(), eq(USER_ID), eq(ID))).thenReturn(venueResponse);

    mockMvc.perform(put("/users/" + USER_ID + "/venues/" + ID)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isNoContent());
  }

  @Test
  public void testGetVenuesFiltered() throws Exception {
    when(venueService.getVenuesFiltered(TYPE, CITY, RATING)).thenReturn(Collections.singletonList(venueResponse));
    mockMvc.perform(get("/venues")
                      .param("type", TYPE)
                      .param("city", String.valueOf(CITY))
                      .param("rating", String.valueOf(RATING)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].name").value(NAME))
           .andExpect(jsonPath("$[0].address").value(ADDRESS))
           .andExpect(jsonPath("$[0].venueType").value(TYPE))
           .andExpect(jsonPath("$[0].phoneNumber").value(PHONE))
           .andExpect(jsonPath("$[0].emailAddress").value(EMAIL))
           .andExpect(jsonPath("$[0].webSiteUrl").value(WEB))
           .andExpect(jsonPath("$[0].numberOfReviews").value(NUMBER_REVIEWS))
           .andExpect(jsonPath("$[0].rating").value(RATING));
  }

}