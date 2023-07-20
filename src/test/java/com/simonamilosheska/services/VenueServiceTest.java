package com.simonamilosheska.services;

import com.simonamilosheska.exceptions.venue.NotExistingVenueException;
import com.simonamilosheska.mappers.VenueMapper;
import com.simonamilosheska.models.City;
import com.simonamilosheska.models.Venue;
import com.simonamilosheska.models.enums.VenueTypeEnum;
import com.simonamilosheska.repositories.VenueRepository;
import com.simonamilosheska.requests.VenueRequest;
import com.simonamilosheska.responses.UserResponse;
import com.simonamilosheska.responses.VenueResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.simonamilosheska.constants.CityConstants.CITY_ID;
import static com.simonamilosheska.constants.CityConstants.CITY_NAME;
import static com.simonamilosheska.constants.VenueConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VenueServiceTest {

  @Mock
  private VenueRepository venueRepository;
  @Mock
  private VenueMapper venueMapper;
  @Mock
  private UserService userService;
  @Mock
  private CityService cityService;

  @InjectMocks
  private VenueService venueService;

  @Test
  public void testGetVenues() {
    List<Venue> venues =
      Collections.singletonList(new Venue(ID, NAME, ADDRESS, CITY, USER_ID, VenueTypeEnum.HOTEL, PHONE, EMAIL, WEB));
    when(venueRepository.getAllVenues()).thenReturn(venues);

    List<VenueResponse> venueResponses =
      Collections.singletonList(new VenueResponse(ID, NAME, ADDRESS, TYPE, PHONE, EMAIL, WEB, NUMBER_REVIEWS, RATING));
    when(venueMapper.getVenueResponses(venues)).thenReturn(venueResponses);

    List<VenueResponse> result = venueService.getAllVenues();

    assertEquals(venueResponses.get(0).getId(), result.get(0).getId());
    verify(venueRepository, times(1)).getAllVenues();
  }

  @Test
  public void testGetVenuesById() {
    Venue venue = new Venue(ID, NAME, ADDRESS, CITY, USER_ID, VenueTypeEnum.HOTEL, PHONE, EMAIL, WEB);
    when(venueRepository.getVenueById(ID)).thenReturn(venue);

    Venue resultVenue = venueService.getVenueById(ID);

    assertNotNull(resultVenue);
    assertEquals(venue.getId(), resultVenue.getId());
    verify(venueRepository, times(1)).getVenueById(ID);
  }

  @Test
  public void testCreateVenue() {
    when(userService.getUserById(USER_ID)).thenReturn(new UserResponse());
    when(cityService.getCityById(CITY)).thenReturn(new City());
    when(venueRepository.isNotExistVenue(NAME, CITY)).thenReturn(true);

    VenueRequest venueRequest = new VenueRequest(NAME, ADDRESS, CITY, TYPE, PHONE, EMAIL, WEB);
    Venue venue = new Venue(ID, NAME, ADDRESS, CITY, USER_ID, VenueTypeEnum.HOTEL, PHONE, EMAIL, WEB);
    when(venueMapper.getVenue(venueRequest, USER_ID)).thenReturn(venue);
    when(venueRepository.createVenue(venue)).thenReturn(venue);

    Venue resultVenue = venueService.createVenue(venueRequest, USER_ID);
    assertEquals(venue.getId(), resultVenue.getId());

    verify(venueRepository, times(1)).createVenue(venue);
    verify(venueRepository, times(1)).isNotExistVenue(NAME, CITY);
  }

  @Test(expected = NotExistingVenueException.class)
  public void testGetVenueById_exception() {

    when(venueRepository.getVenueById(ID)).thenThrow(new NotExistingVenueException(ID));
    venueService.getVenueById(ID);
    verify(venueRepository).getVenueById(ID);
  }

  @Test
  public void testGetVenuesFiltered() {
    Venue venue = new Venue(ID, NAME, ADDRESS, CITY, USER_ID, VenueTypeEnum.HOTEL, PHONE, EMAIL, WEB);
    VenueResponse venueResponse = new VenueResponse(ID, NAME, ADDRESS, VenueTypeEnum.HOTEL.name(), PHONE,
                                                    EMAIL, WEB, NUMBER_REVIEWS, RATING);

    when(cityService.getCityById(CITY_ID)).thenReturn(new City(CITY, CITY_NAME));
    when(venueRepository.getVenuesFiltered(TYPE, CITY, RATING)).thenReturn(Collections.singletonList(venue));
    when(venueMapper.getVenueResponses(Collections.singletonList(venue))).thenReturn(
      Collections.singletonList(venueResponse));
    List<VenueResponse> result = venueService.getVenuesFiltered(TYPE, CITY, RATING);


    assertEquals(venueResponse.getAddress(), result.get(0).getAddress());
    verify(cityService, times(1)).getCityById(CITY);
    verify(venueRepository, times(1)).getVenuesFiltered(TYPE, CITY, RATING);
    verify(venueMapper, times(1)).getVenueResponses(Collections.singletonList(venue));
  }

}