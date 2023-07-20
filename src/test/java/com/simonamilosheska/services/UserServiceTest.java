package com.simonamilosheska.services;

import com.simonamilosheska.mappers.CityMapper;
import com.simonamilosheska.mappers.UserMapper;
import com.simonamilosheska.models.City;
import com.simonamilosheska.models.User;
import com.simonamilosheska.repositories.UserRepository;

import com.simonamilosheska.requests.UserRequest;
import com.simonamilosheska.responses.CityResponse;
import com.simonamilosheska.responses.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;


import static com.simonamilosheska.constants.UserConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserService userService;

  @Test
  public void testGetUsers() {
    List<User> users = Collections.singletonList(new User(ID, NAME, USERNAME, EMAIL, CITY_FK));
    when(userRepository.getAllUsers()).thenReturn(users);

    List<UserResponse> userResponses =
      Collections.singletonList(new UserResponse(ID, NAME, USERNAME, EMAIL, CITY_FK, REVIEWS, DATE));
    when(userMapper.getUserResponses(users)).thenReturn(userResponses);

    List<UserResponse> result = userService.getAllUsers();

    assertEquals(userResponses.get(0).getId(), result.get(0).getId());
    verify(userRepository, times(1)).getAllUsers();
  }
  @Test
  public void testGetUserById() {
    User user = new User(ID, NAME, USERNAME, EMAIL, CITY_FK);
    when(userRepository.getUserById(ID)).thenReturn(user);

    UserResponse userResponse = new UserResponse(ID, NAME, USERNAME, EMAIL, CITY_FK, REVIEWS, DATE);
    when(userMapper.getUserResponses(user)).thenReturn(userResponse);

    UserResponse resultUser = userService.getUserById(ID);

    assertEquals(user.getId(), resultUser.getId());
    verify(userRepository, times(1)).getUserById(ID);
  }

  @Test
  public void testGetUserByUsername() {
    User user = new User(ID, NAME, USERNAME, EMAIL, CITY_FK);
    when(userRepository.getUserByUsername(USERNAME)).thenReturn(user);

    UserResponse userResponse = new UserResponse(ID, NAME, USERNAME, EMAIL, CITY_FK, REVIEWS, DATE);
    when(userMapper.getUserResponses(user)).thenReturn(userResponse);

    UserResponse resultUser = userService.getUserByUsername(USERNAME);

    assertEquals(user.getId(), resultUser.getId());
    verify(userRepository, times(1)).getUserByUsername(USERNAME);
  }

  @Test
  public void testGetUserByEmailAddress() {
    User user = new User(ID, NAME, USERNAME, EMAIL, CITY_FK);
    when(userRepository.getUserByEmailAddress(EMAIL)).thenReturn(user);

    UserResponse userResponse = new UserResponse(ID, NAME, USERNAME, EMAIL, CITY_FK, REVIEWS, DATE);
    when(userMapper.getUserResponses(user)).thenReturn(userResponse);

    UserResponse resultUser = userService.getUserByEmailAddress(EMAIL);

    assertEquals(user.getId(), resultUser.getId());
    verify(userRepository, times(1)).getUserByEmailAddress(EMAIL);
  }

  @Test
  public void testCreateUser() {
    User user = new User(ID, NAME, USERNAME, EMAIL, CITY_FK);
    when(userRepository.createUser(user)).thenReturn(user);

    UserRequest userRequest = new UserRequest( NAME, USERNAME, EMAIL, CITY_FK);
    when(userMapper.getUser(userRequest)).thenReturn(user);

    User resultUser = userService.createUser(userRequest);

    assertEquals(user.getId(), resultUser.getId());
    verify(userRepository, times(1)).createUser(user);
  }


}