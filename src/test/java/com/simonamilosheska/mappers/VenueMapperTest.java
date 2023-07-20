package com.simonamilosheska.mappers;

import com.simonamilosheska.models.Venue;
import com.simonamilosheska.models.enums.VenueTypeEnum;
import com.simonamilosheska.requests.VenueRequest;
import com.simonamilosheska.responses.VenueResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static com.simonamilosheska.constants.VenueConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class VenueMapperTest {

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  private VenueMapper venueMapper;


  @Test(expected = RuntimeException.class)
  public void testGetVenuesFromResultSet_exception() throws SQLException {
    when(resultSet.next()).thenThrow(new SQLException());

    venueMapper.getVenuesFromResultSet(resultSet);
  }

  @Test
  public void testGetVenue() {
    VenueRequest venueRequest = new VenueRequest(NAME, ADDRESS, CITY, TYPE, PHONE, EMAIL, WEB);
    Venue venue = venueMapper.getVenue(venueRequest, USER_ID);

    assertNotNull(venue);
    assertEquals(venueRequest.getAddress(), venue.getAddress());
  }

  @Test
  public void testGetUserResponse() {
    Venue venue = new Venue(ID, NAME, ADDRESS, CITY, USER_ID, VenueTypeEnum.HOTEL, PHONE, EMAIL, WEB);


    VenueResponse venueResponse = venueMapper.getVenueResponse(venue);

    assertEquals(venue.getId(), venueResponse.getId());
    assertEquals(venue.getName(), venueResponse.getName());
    assertNotNull(venueResponse);
  }

  @Test
  public void testGetUsersResponse() {
    List<Venue> list =
      Collections.singletonList(new Venue(ID, NAME, ADDRESS, CITY, USER_ID, VenueTypeEnum.HOTEL, PHONE, EMAIL, WEB));

    List<VenueResponse> actual = venueMapper.getVenueResponses(list);

    assertEquals(list.size(), actual.size());
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i).getId(), actual.get(i).getId());
    }
  }
}