package com.simonamilosheska.mappers;

import com.simonamilosheska.models.Review;
import com.simonamilosheska.models.User;
import com.simonamilosheska.requests.ReviewRequest;
import com.simonamilosheska.requests.UserRequest;
import com.simonamilosheska.responses.ReviewResponse;
import com.simonamilosheska.responses.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.simonamilosheska.constants.UserConstants.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  private UserMapper userMapper;
  @Test
  public void testGetUsersFromResultSet() throws SQLException {
    when(resultSet.next()).thenReturn(true).thenReturn(false);
    when(resultSet.getInt(anyInt())).thenReturn(ID);
    when(resultSet.getString(anyInt())).thenReturn(NAME);
    when(resultSet.getString(anyInt())).thenReturn(USERNAME);
    when(resultSet.getString(anyInt())).thenReturn(EMAIL);
    when(resultSet.getInt(anyInt())).thenReturn(CITY_FK);
    Array reviewIds1 = mock(Array.class);
    when(reviewIds1.getArray()).thenReturn(new Integer[] {});
    when(resultSet.getArray(anyInt())).thenReturn(reviewIds1);
    when(resultSet.getDate(anyInt())).thenReturn(Date.valueOf(LocalDate.now()));
    when(resultSet.getDate(anyInt())).thenReturn(Date.valueOf(LocalDate.now()));


    List<User> userList = userMapper.getUsersFromResultSet(resultSet);

    assertEquals(1, userList.size());
    assertEquals(EMAIL, userList.get(0).getEmailAddress());
  }

  @Test(expected = RuntimeException.class)
  public void testGetUsersFromResultSet_exception() throws SQLException {
    when(resultSet.next()).thenThrow(new SQLException());

    userMapper.getUsersFromResultSet(resultSet);
  }

  @Test
  public void testGetUser(){
    UserRequest userRequest=new UserRequest(NAME,USERNAME,EMAIL,CITY_FK);
    User user=userMapper.getUser(userRequest);

    assertNotNull(user);
    assertEquals(userRequest.getUsername(),user.getUsername());
  }

  @Test
  public void testGetUserResponse(){
    User user=new User(ID,NAME,USERNAME,EMAIL,CITY_FK,LocalDate.now());

    UserResponse userResponse = userMapper.getUserResponses(user);

    assertEquals(userResponse.getId(),userResponse.getId());
    assertEquals(user.getUsername(),userResponse.getUsername());
    assertNotNull(userResponse);

  }

  @Test
  public void testGetUsersResponse(){
    List<User> list = Collections.singletonList(new User(ID,NAME,USERNAME,EMAIL,CITY_FK,LocalDate.now()));

    List<UserResponse> actual = userMapper.getUserResponses(list);

    assertEquals(list.size(), actual.size());
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i).getId(), actual.get(i).getId());
    }


}}