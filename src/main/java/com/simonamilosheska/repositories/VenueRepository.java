package com.simonamilosheska.repositories;

import com.simonamilosheska.exceptions.DataBaseException;
import com.simonamilosheska.exceptions.EmptyListException;
import com.simonamilosheska.exceptions.venue.NotExistingVenueException;
import com.simonamilosheska.exceptions.venue.AlreadyExistVenueException;
import com.simonamilosheska.exceptions.venue.NotExistingVenueByCreatorException;
import com.simonamilosheska.mappers.VenueMapper;
import com.simonamilosheska.models.Venue;
import com.simonamilosheska.models.enums.VenueTypeEnum;
import com.simonamilosheska.requests.VenueRequestUpdate;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Repository
public class VenueRepository   {

  private final HikariDataSource hikariDataSource;
  private final VenueMapper venueMapper;

  public VenueRepository(HikariDataSource hikariDataSource, VenueMapper venueMapper) {
    this.hikariDataSource = hikariDataSource;
    this.venueMapper = venueMapper;
  }

  public static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
    return (!resultSet.isBeforeFirst() && resultSet.getRow() == 0);
  }

  public List<Venue> getAllVenues() {
    String query =
      "SELECT v.id, v.name, v.address, v.cityfk, v.createdbyuserfk, v.venuetype, v.phonenumber, v.emailaddress, v" +
      ".website, r.review_count, r.avg_rating " +
      "FROM trippy.trippysh.venue as v " +
      "LEFT JOIN(" +
      "SELECT venuefk, COUNT(*) AS review_count,  COALESCE(ROUND(AVG(rating), 2), 0)  as avg_rating " +
      "FROM trippy.trippysh.review as r " +
      "GROUP BY venuefk )as r ON venuefk = v.id ";

    try (Connection connection = hikariDataSource.getConnection();
         Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(query);
      if (!isResultSetEmpty(resultSet)) {
        return venueMapper.getVenuesFromResultSet(resultSet);
      } else {
        throw new EmptyListException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Venue getVenueById(int venueId) {
    String query =
      "SELECT v.id, v.name, v.address, v.cityfk, v.createdbyuserfk, v.venuetype, v.phonenumber, v.emailaddress, v" +
      ".website, r.review_count, r.avg_rating " +
      "FROM trippy.trippysh.venue as v " +
      "LEFT JOIN(" +
      "SELECT venuefk, COUNT(*) AS review_count,  COALESCE(ROUND(AVG(rating), 2), 0)  as avg_rating " +
      "FROM trippy.trippysh.review as r " +
      "GROUP BY venuefk )as r ON venuefk = v.id " +
      "WHERE v.id=?";

    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, venueId);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return venueMapper.getVenuesFromResultSet(resultSet).get(0);
      } else {
        throw new NotExistingVenueException(venueId);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Venue> getVenuesFiltered(String type, int cityId, double rating) {
    String query =
      "SELECT v.id, v.name, v.address, v.cityfk, v.createdbyuserfk, v.venuetype, v.phonenumber, v.emailaddress, v" +
      ".website, review_count, avg_rating " +
      "FROM trippy.trippysh.venue as v " +
      "LEFT JOIN(" +
      "SELECT venuefk, COUNT(*) AS review_count,  COALESCE(ROUND(AVG(rating), 2), 0)  as avg_rating " +
      "FROM trippy.trippysh.review as r " +
      "GROUP BY venuefk )as r ON venuefk = v.id " +
      "WHERE v.venuetype=? and v.cityfk=? and r.avg_rating=?";

    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, VenueTypeEnum.valueOf(type.toUpperCase(Locale.ROOT)).ordinal());
      preparedStatement.setInt(2, cityId);
      preparedStatement.setDouble(3, rating);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return venueMapper.getVenuesFromResultSet(resultSet);
      } else {
        throw new EmptyListException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Venue getVenueByIdAndUserId(int venueId, int creatorId) {
    List<Venue> venues;
    String query =
      "SELECT v.id, v.name, v.address, v.cityfk, v.createdbyuserfk, v.venuetype, v.phonenumber, v.emailaddress, v" +
      ".website, review_count, avg_rating " +
      "FROM trippy.trippysh.venue as v " +
      "LEFT JOIN(" +
      "SELECT venuefk, COUNT(*) AS review_count,  COALESCE(ROUND(AVG(rating), 2), 0)  as avg_rating " +
      "FROM trippy.trippysh.review as r " +
      "GROUP BY venuefk )as r ON venuefk = v.id " +
      "WHERE v.id=? and v.createdbyuserfk=?";

    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, venueId);
      preparedStatement.setInt(2, creatorId);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return venueMapper.getVenuesFromResultSet(resultSet).get(0);
      } else {
        throw new NotExistingVenueByCreatorException(venueId, creatorId);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isNotExistVenue(String name, int cityId) {
    String query =
      "SELECT id " +
      "FROM trippy.trippysh.venue " +
      "WHERE name=? and cityfk=?";
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, cityId);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        throw new AlreadyExistVenueException(name, cityId);
      }
      else return true;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Venue createVenue(Venue venue) {
    final String insertSQL = "INSERT INTO trippy.trippysh.venue( name, address, cityfk,createdbyuserfk," +
                             "venuetype,phonenumber,emailaddress,website,createdon) VALUES (?,?,?,?,?,?,?,?,?)";
    int venueId;
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                                                           Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, venue.getName());
      preparedStatement.setString(2, venue.getEmailAddress());
      preparedStatement.setInt(3, venue.getCityFk());
      preparedStatement.setInt(4, venue.getCreatorId());
      preparedStatement.setInt(5, venue.getVenueType().ordinal());
      preparedStatement.setString(6, venue.getPhoneNumber());
      preparedStatement.setString(7, venue.getEmailAddress());
      preparedStatement.setString(8, venue.getWebsiteUrl());
      preparedStatement.setDate(9, Date.valueOf(LocalDate.now()));
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        venueId = resultSet.getInt(1);
      } else {
        throw new SQLException("No id retrieved from inserted object");
      }
    } catch (SQLException e) {
      throw new DataBaseException(e.getMessage());
    }
    venue.setId(venueId);
    return venue;
  }

  public int editVenue(VenueRequestUpdate venueRequestUpdate, int venueId) {
    final String updateSQL =
      "UPDATE trippy.trippysh.venue SET address = ?, phonenumber = ? , emailaddress=?, website=? WHERE id = ?";
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
      updateStatement.setString(1, venueRequestUpdate.getAddress());
      updateStatement.setString(2, venueRequestUpdate.getPhoneNumber());
      updateStatement.setString(3, venueRequestUpdate.getEmailAddress());
      updateStatement.setString(4, venueRequestUpdate.getWebsiteUrl());
      updateStatement.setInt(5, venueId);
      return updateStatement.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
