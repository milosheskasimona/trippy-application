package com.simonamilosheska.repositories;

import com.simonamilosheska.exceptions.DataBaseException;
import com.simonamilosheska.exceptions.EmptyListException;
import com.simonamilosheska.exceptions.user.AlreadyExistUserException;
import com.simonamilosheska.exceptions.user.NotExistingEmailAddressException;
import com.simonamilosheska.exceptions.user.NotExistingUserException;
import com.simonamilosheska.exceptions.user.NotExistingUsernameException;
import com.simonamilosheska.mappers.UserMapper;
import com.simonamilosheska.models.User;
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

@Repository
public class UserRepository {

  private final HikariDataSource hikariDataSource;
  private final UserMapper userMapper;

  public UserRepository(HikariDataSource hikariDataSource, UserMapper userMapper) {
    this.hikariDataSource = hikariDataSource;
    this.userMapper = userMapper;
  }

  public static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
    return (!resultSet.isBeforeFirst() && resultSet.getRow() == 0);
  }

  public List<User> getAllUsers() {
    String query =
      "SELECT u.id,u.name,u.username,u.emailaddress,u.cityfk, array_agg(r.id),u.createdon,u.deletedon FROM trippy" +
      ".trippysh.user as u " +
      " LEFT JOIN trippy.trippysh.review as r on u.id = r.userfk group by u.id";
    try (Connection connection = hikariDataSource.getConnection();
         Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery(query);
      if (!isResultSetEmpty(resultSet)) {
        return userMapper.getUsersFromResultSet(resultSet);
      } else {
        throw new EmptyListException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public User getUserById(int id) {
    String query =
      "SELECT u.id,u.name,u.username,u.emailaddress,u.cityfk, array_agg(r.id),u.createdon,u.deletedon FROM trippy" +
      ".trippysh.user as u " +
      " LEFT JOIN trippy.trippysh.review as r on u.id = r.userfk  where u.id= ? group by u.id ";
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return userMapper.getUsersFromResultSet(resultSet).get(0);
      } else {
        throw new NotExistingUserException(id);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public User getUserByUsername(String username) {
    String query =
      "SELECT u.id,u.name,u.username,u.emailaddress,u.cityfk, array_agg(r.id),u.createdon,u.deletedon FROM trippy" +
      ".trippysh.user as u " +
      " LEFT JOIN trippy.trippysh.review as r on u.id = r.userfk  where u.username= ? group by u.id ";
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, username);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return userMapper.getUsersFromResultSet(resultSet).get(0);
      } else {
        throw new NotExistingUsernameException(username);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public User getUserByEmailAddress(String emailAddress) {
    String query =
      "SELECT u.id,u.name,u.username,u.emailaddress,u.cityfk, array_agg(r.id),u.createdon,u.deletedon FROM trippy" +
      ".trippysh.user as u " +
      " LEFT JOIN trippy.trippysh.review as r on u.id = r.userfk  where u.emailaddress= ? group by u.id ";
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, emailAddress);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return userMapper.getUsersFromResultSet(resultSet).get(0);
      } else {
        throw new NotExistingEmailAddressException(emailAddress);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public User createUser(User user) {
    final String insertSQL = "INSERT INTO trippy.trippysh.user( name, username, emailaddress, cityfk, createdon) VALUES (?,?,?,?,?)";
    int userId;
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                                                           Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, user.getName());
      preparedStatement.setString(2, user.getUsername());
      preparedStatement.setString(3, user.getEmailAddress());
      preparedStatement.setInt(4, user.getCityFk());
      preparedStatement.setDate(5,Date.valueOf(LocalDate.now()));
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        userId = resultSet.getInt(1);
      } else {
        throw new SQLException("No id retrieved from inserted object");
      }
    } catch (SQLException e) {
      throw new DataBaseException(e.getMessage());
    }
    user.setId(userId);
    return user;
  }
}
