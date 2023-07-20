package com.simonamilosheska.repositories;

import com.simonamilosheska.exceptions.DataBaseException;
import com.simonamilosheska.exceptions.EmptyListException;
import com.simonamilosheska.exceptions.review.NotExistingReviewByUserException;
import com.simonamilosheska.exceptions.review.NotExistingReviewException;
import com.simonamilosheska.mappers.ReviewMapper;
import com.simonamilosheska.models.Review;
import com.simonamilosheska.requests.ReviewRequestUpdate;
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
public class ReviewRepository {

  private final HikariDataSource hikariDataSource;
  private final ReviewMapper reviewMapper;

  public ReviewRepository(HikariDataSource hikariDataSource, ReviewMapper reviewMapper) {
    this.hikariDataSource = hikariDataSource;
    this.reviewMapper = reviewMapper;
  }

  public static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException {
    return (!resultSet.isBeforeFirst() && resultSet.getRow() == 0);
  }

  public List<Review> getReviewsByVenueId(int venueId) {
    String query = "SELECT *" +
                   "FROM trippy.trippysh.review " +
                   "WHERE venuefk=? and deletedon IS NULL";

    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, venueId);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return reviewMapper.getReviewsFromResultSet(resultSet);
      } else {
        throw new EmptyListException();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Review getReviewById(int id) {
    String query = "SELECT *" +
                   "FROM trippy.trippysh.review " +
                   "WHERE id=? and deletedon IS NULL ";

    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return reviewMapper.getReviewsFromResultSet(resultSet).get(0);
      } else {
        throw new NotExistingReviewException(id);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Review getReviewByIdAndUser(int id, int userId) {
    String query = "SELECT *" +
                   "FROM trippy.trippysh.review " +
                   "WHERE id=? and userfk=? and deletedon IS  NULL";

    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, userId);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (!isResultSetEmpty(resultSet)) {
        return reviewMapper.getReviewsFromResultSet(resultSet).get(0);
      } else {
        throw new NotExistingReviewByUserException(id, userId);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Review createReview(Review review) {
    final String insertSQL =
      "INSERT INTO trippy.trippysh.review(venuefk, userfk, rating, reviewcomment, createdon) VALUES (?,?,?,?,?)";
    int reviewId;
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                                                                           Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setInt(1, review.getVenueFk());
      preparedStatement.setInt(2, review.getUserFk());
      preparedStatement.setInt(3, review.getRating());
      preparedStatement.setString(4, review.getReviewComment());
      preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
      preparedStatement.executeUpdate();
      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        reviewId = resultSet.getInt(1);
      } else {
        throw new SQLException("No id retrieved from inserted object");
      }
    } catch (SQLException e) {
      throw new DataBaseException(e.getMessage());
    }
    review.setId(reviewId);
    return review;
  }

  public int editReview(ReviewRequestUpdate reviewRequestUpdate, int reviewId) {
    final String updateSQL =
      "UPDATE trippy.trippysh.review SET rating = ?, reviewcomment = ? WHERE id = ?";
    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement updateStatement = connection.prepareStatement(updateSQL,
                                                                         Statement.RETURN_GENERATED_KEYS)) {
      updateStatement.setInt(1, reviewRequestUpdate.getRating());
      updateStatement.setString(2, reviewRequestUpdate.getReviewComment());
      updateStatement.setInt(3, reviewId);
      return updateStatement.executeUpdate();
    } catch (SQLException e) {
      System.err.println("Error executing query: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public int deleteReview(int reviewId) {
    final String deleteSQL = "UPDATE trippy.trippysh.review SET deletedon = ? WHERE id = ?";


    try (Connection connection = hikariDataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
      preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));
      preparedStatement.setInt(2, reviewId);
      return preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
