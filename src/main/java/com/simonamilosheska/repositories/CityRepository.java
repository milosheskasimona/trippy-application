package com.simonamilosheska.repositories;

import com.simonamilosheska.exceptions.EmptyListException;
import com.simonamilosheska.exceptions.city.NotExistingCityException;
import com.simonamilosheska.mappers.CityMapper;
import com.simonamilosheska.models.City;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class CityRepository {

  private final HikariDataSource hikariDataSource;
  private final CityMapper cityMapper;

  public CityRepository(HikariDataSource hikariDataSource,CityMapper cityMapper) {
    this.hikariDataSource = hikariDataSource;
    this.cityMapper=cityMapper;
  }

  public static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
    return (!resultSet.isBeforeFirst() && resultSet.getRow() == 0);
  }

  public List<City> getAllCities() {
    String query = "SELECT * FROM trippy.trippysh.City";
    try (Connection connection = hikariDataSource.getConnection();
         Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(query);
      if (!isResultSetEmpty(resultSet)) {
        return cityMapper.getCityFromResultSet(resultSet);
      } else {
        throw new EmptyListException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public City getCityById(int id) {
    String query = "SELECT * FROM trippy.trippysh.City WHERE id=?";
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return cityMapper.getCityFromResultSet(resultSet).get(0);
      } else {
        throw new NotExistingCityException(id);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
