package com.simonamilosheska.mappers;

import com.simonamilosheska.models.City;
import com.simonamilosheska.responses.CityResponse;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapper {

  public  List<City> getCityFromResultSet(ResultSet resultSet) {
    List<City> cities = new ArrayList<>();
    try (resultSet) {
      while (resultSet.next()) {
        City city = new City();
        city.setId(resultSet.getInt(1));
        city.setName(resultSet.getString(2));
        city.setCreatedOn(resultSet.getDate(3).toLocalDate());
        Date deletedOn = resultSet.getDate(4);
        if (deletedOn != null) {
          city.setDeletedOn(deletedOn.toLocalDate());
        }
        cities.add(city);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return cities;
  }

  public  List<CityResponse> getCityResponses(List<City> cities) {
    List<CityResponse> cityResponses = new ArrayList<>();
    for (City c : cities) {
      cityResponses.add(new CityResponse(c.getId(), c.getName()));
    }
    return cityResponses;
  }
}
