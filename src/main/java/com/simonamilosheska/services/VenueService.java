package com.simonamilosheska.services;

import com.simonamilosheska.exceptions.venue.InvalidEnumInputException;
import com.simonamilosheska.mappers.VenueMapper;
import com.simonamilosheska.models.Venue;
import com.simonamilosheska.models.enums.VenueTypeEnum;
import com.simonamilosheska.repositories.VenueRepository;
import com.simonamilosheska.requests.VenueRequest;
import com.simonamilosheska.requests.VenueRequestUpdate;
import com.simonamilosheska.responses.VenueResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

  private VenueRepository venueRepository;
  private VenueMapper venueMapper;
  private UserService userService;
  private CityService cityService;

  public VenueService(
    VenueRepository venueRepository, VenueMapper venueMapper, UserService userService, CityService cityService
  ) {
    this.venueRepository = venueRepository;
    this.venueMapper = venueMapper;
    this.userService = userService;
    this.cityService = cityService;
  }

  public List<VenueResponse> getAllVenues() {
    return venueMapper.getVenueResponses(venueRepository.getAllVenues());
  }

  public List<VenueResponse> getVenuesFiltered(String type, int cityId, double rating) {
    try {
      VenueTypeEnum.valueOf(type);
    } catch (IllegalArgumentException e) {
      throw new InvalidEnumInputException(type);
    }
    cityService.getCityById(cityId);

    return venueMapper.getVenueResponses(venueRepository.getVenuesFiltered(type, cityId, rating));
  }

  public Venue getVenueById(int venueId) {
    return venueRepository.getVenueById(venueId);
  }

  public Venue createVenue(VenueRequest venueRequest, int userId) {
    userService.getUserById(userId);
    cityService.getCityById(venueRequest.getCityId());
    venueRepository.isNotExistVenue(venueRequest.getName(), venueRequest.getCityId());
    return venueRepository.createVenue(venueMapper.getVenue(venueRequest,userId));
  }

  public VenueResponse editVenue(VenueRequestUpdate venueRequestUpdate, int creatorId, int venueId) {
    userService.getUserById(creatorId);
    getVenueById(venueId);
    venueRepository.getVenueByIdAndUserId(venueId, creatorId);
    venueRepository.editVenue(venueRequestUpdate, venueId);
    return venueMapper.getVenueResponse(getVenueById(venueId));
  }
}
