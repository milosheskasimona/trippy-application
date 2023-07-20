package com.simonamilosheska.mappers;

import com.simonamilosheska.models.City;
import com.simonamilosheska.responses.CityResponse;
import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.simonamilosheska.constants.CityConstants.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class CityMapperTest {
  @Mock
  private ResultSet resultSet;

  @InjectMocks
  private CityMapper cityMapper;

  @Before
  public void setUp() {
    cityMapper = new CityMapper();
  }

  @Test
  public void testGetCityFromResultSet() throws SQLException {
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getInt(anyInt())).thenReturn(CITY_ID);
    when(resultSet.getString(anyInt())).thenReturn(CITY_NAME);
    when(resultSet.getDate(anyInt())).thenReturn(Date.valueOf(LocalDate.now()));

    List<City> cityList = cityMapper.getCityFromResultSet(resultSet);

    assertEquals(1, cityList.size());
    assertEquals(CITY_NAME, cityList.get(0).getName());
  }

  @Test(expected = RuntimeException.class)
  public void testGetCityFromResultSet_exception() throws SQLException {
    when(resultSet.next()).thenThrow(new SQLException());

    cityMapper.getCityFromResultSet(resultSet);
  }

  @Test
  public void testGetCityResponses(){
    List<City> cities= Collections.singletonList(new City(CITY_ID,CITY_NAME));
    List<CityResponse> cityResponses =cityMapper.getCityResponses(cities);

    assertEquals(cities.get(0).getId(),cityResponses.get(0).getId());
    assertEquals(cities.get(0).getName(),cityResponses.get(0).getName());


  }

}