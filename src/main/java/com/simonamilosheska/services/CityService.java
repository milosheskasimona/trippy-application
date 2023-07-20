package com.simonamilosheska.services;

import com.simonamilosheska.mappers.CityMapper;
import com.simonamilosheska.models.City;
import com.simonamilosheska.repositories.CityRepository;
import com.simonamilosheska.responses.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

  private CityRepository cityRepository;
  private CityMapper cityMapper;

  @Autowired
  public CityService(CityRepository cityRepository, CityMapper cityMapper) {
    this.cityRepository = cityRepository;
    this.cityMapper = cityMapper;
  }

  public List<CityResponse> getAllCities() {
    return cityMapper.getCityResponses(cityRepository.getAllCities());
  }
  public City getCityById(int id){
    return cityRepository.getCityById(id);
  }
}
