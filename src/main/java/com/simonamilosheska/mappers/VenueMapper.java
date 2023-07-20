package com.simonamilosheska.mappers;

import com.simonamilosheska.models.Venue;
import com.simonamilosheska.models.enums.VenueTypeEnum;
import com.simonamilosheska.requests.VenueRequest;
import com.simonamilosheska.responses.VenueResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class VenueMapper {

  public List<Venue> getVenuesFromResultSet(ResultSet resultSet) {
    List<Venue> venues = new ArrayList<>();
    try (resultSet) {
      while (resultSet.next()) {
        Venue venue = new Venue();
        venue.setId(resultSet.getInt(1));
        venue.setName(resultSet.getString(2));
        venue.setAddress(resultSet.getString(3));
        venue.setCityFk(resultSet.getInt(4));
        venue.setCreatorId(resultSet.getInt(5));
        venue.setVenueType(VenueTypeEnum.values()[resultSet.getInt(6)]);
        venue.setPhoneNumber(resultSet.getString(7));
        venue.setEmailAddress(resultSet.getString(8));
        venue.setWebsiteUrl(resultSet.getString(9));
        venue.setNumberOfReviews(resultSet.getInt(10));
        venue.setRating(resultSet.getDouble(11));
        venues.add(venue);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return venues;
  }

  public List<VenueResponse> getVenueResponses(List<Venue> venues) {
    List<VenueResponse> venueResponses = new ArrayList<>();
    for (Venue venue : venues) {
      venueResponses.add(
        new VenueResponse(venue.getId(), venue.getName(), venue.getAddress(), venue.getVenueType().name(),
                          venue.getPhoneNumber(),
                          venue.getEmailAddress(), venue.getWebsiteUrl(), venue.getNumberOfReviews(),
                          venue.getRating()));
    }
    return venueResponses;
  }

  public Venue getVenue(VenueRequest venueRequest, int userId) {
    return new Venue(venueRequest.getName(), venueRequest.getAddress(),
                     venueRequest.getCityId(), userId,
                     VenueTypeEnum.valueOf(venueRequest.getVenueType().toUpperCase(Locale.ROOT)),
                     venueRequest.getPhoneNumber(),
                     venueRequest.getEmailAddress(),
                     venueRequest.getWebSiteUrl());
  }



  public VenueResponse getVenueResponse(Venue venue) {
    return new VenueResponse(venue.getId(), venue.getName(), venue.getAddress(), venue.getVenueType().name(),
                             venue.getPhoneNumber(), venue.getEmailAddress(), venue.getWebsiteUrl(),
                             venue.getNumberOfReviews(), venue.getRating());
  }
}
