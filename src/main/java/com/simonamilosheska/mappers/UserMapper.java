package com.simonamilosheska.mappers;

import com.simonamilosheska.models.User;
import com.simonamilosheska.requests.UserRequest;
import com.simonamilosheska.responses.UserResponse;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserMapper {

  public List<User> getUsersFromResultSet(ResultSet resultSet) {
    List<User> users = new ArrayList<>();
    try (resultSet) {
      while (resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        user.setUsername(resultSet.getString(3));
        user.setEmailAddress(resultSet.getString(4));
        user.setCityId(resultSet.getInt(5));
        Array reviewIds = resultSet.getArray(6);
        List<Integer> list = new ArrayList<>(Arrays.asList((Integer[]) reviewIds.getArray()));
        user.setReviews(list);
        user.setCreatedOn(resultSet.getDate(7).toLocalDate());
        Date deletedOn = resultSet.getDate(8);
        if (deletedOn != null) {
          user.setDeletedOn(deletedOn.toLocalDate());
        }
        users.add(user);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return users;
  }

  public List<UserResponse> getUserResponses(List<User> users) {
    List<UserResponse> userResponses = new ArrayList<>();
    for (User u : users) {
      userResponses.add(
        new UserResponse(u.getId(), u.getName(), u.getUsername(), u.getEmailAddress(), u.getCityFk(),
                         u.getReviews(),u.getCreatedOn().toString()));
    }
    return userResponses;
  }

  public UserResponse getUserResponses(User u) {
    return new UserResponse(u.getId(), u.getName(), u.getUsername(), u.getEmailAddress(), u.getCityFk(),
                            u.getReviews(),u.getCreatedOn().toString());
  }

  public User getUser(UserRequest userRequest) {
    return new User(userRequest.getName(), userRequest.getUsername(), userRequest.getEmailAddress(),
                    userRequest.getCityFk());
  }
}
