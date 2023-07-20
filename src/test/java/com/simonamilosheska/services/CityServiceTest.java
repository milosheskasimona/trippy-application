package com.simonamilosheska.services;

import com.simonamilosheska.mappers.CityMapper;
import com.simonamilosheska.models.City;
import com.simonamilosheska.repositories.CityRepository;
import com.simonamilosheska.responses.CityResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.simonamilosheska.constants.CityConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

  @Mock
  private CityRepository cityRepository;
  @Mock
  private CityMapper cityMapper;

  @InjectMocks
  private CityService cityService;

  @Test
  public void testGetCities() {
    List<City> cities = Collections.singletonList(new City(CITY_ID, CITY_NAME));
    when(cityRepository.getAllCities()).thenReturn(cities);

    List<CityResponse> cityResponses = Collections.singletonList(new CityResponse(CITY_ID, CITY_NAME));
    when(cityMapper.getCityResponses(cities)).thenReturn(cityResponses);

    List<CityResponse> result = cityService.getAllCities();

    assertEquals(cityResponses.get(0).getId(), result.get(0).getId());
    verify(cityRepository, times(1)).getAllCities();
  }

  @Test
  public void testGetCityById() {
    City city = new City(CITY_ID, CITY_NAME);
    when(cityRepository.getCityById(CITY_ID)).thenReturn(city);

    City resultCity = cityService.getCityById(CITY_ID);

    assertEquals(city.getId(), resultCity.getId());
    verify(cityRepository, times(1)).getCityById(CITY_ID);
  }
}