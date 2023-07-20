package com.simonamilosheska.controllers;

import com.simonamilosheska.responses.CityResponse;
import com.simonamilosheska.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CityController {

  private final CityService cityService;

  @Autowired
  public CityController(CityService cityService) {
    this.cityService = cityService;
  }


  @GetMapping("/cities")
  public ResponseEntity<List<CityResponse>> getCities(){
    List<CityResponse> cityResponses = cityService.getAllCities();
    return ResponseEntity.ok(cityResponses);
  }
}
