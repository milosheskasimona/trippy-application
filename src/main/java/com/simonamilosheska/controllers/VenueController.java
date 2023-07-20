package com.simonamilosheska.controllers;

import com.simonamilosheska.models.Venue;
import com.simonamilosheska.requests.VenueRequest;
import com.simonamilosheska.requests.VenueRequestUpdate;
import com.simonamilosheska.responses.VenueResponse;
import com.simonamilosheska.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
public class VenueController {

  private final VenueService venueService;

  @Autowired
  public VenueController(VenueService venueService) {
    this.venueService = venueService;
  }

  @GetMapping("/venues")
  public ResponseEntity<List<VenueResponse>> getVenues() {
    List<VenueResponse> venueResponse = venueService.getAllVenues();
    return ResponseEntity.ok(venueResponse);
  }

  @PostMapping("/users/{userId}/venues")
  public ResponseEntity<Void> createVenue(@RequestBody @Valid VenueRequest venueRequest ,@PathVariable int userId) {
    Venue venue = venueService.createVenue(venueRequest, userId);

    URI location = UriComponentsBuilder.fromUriString("/venues/{id}")
                                       .buildAndExpand(venue.getId())
                                       .toUri();

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/users/{userId}/venues/{venueId}")
  public ResponseEntity<VenueResponse> editVenue(
    @RequestBody @Valid VenueRequestUpdate venueRequestUpdate, @PathVariable
    int userId, @PathVariable int venueId) {
    venueService.editVenue(venueRequestUpdate, userId,venueId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/venues", params = {"type", "city", "rating"})
  public ResponseEntity<List<VenueResponse>> getVenuesFiltered(
    @RequestParam(value = "type", required = false) String type,
    @RequestParam(value = "city", required = false) int cityId,
    @RequestParam(value = "rating", required = false) double rating) {

    List<VenueResponse> venueResponses = venueService.getVenuesFiltered(type, cityId, rating);
    return ResponseEntity.ok(venueResponses);
  }
}
