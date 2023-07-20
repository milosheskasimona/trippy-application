package com.simonamilosheska.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simonamilosheska.models.User;
import com.simonamilosheska.requests.UserRequest;
import com.simonamilosheska.responses.UserResponse;
import com.simonamilosheska.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.simonamilosheska.constants.UserConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {



  private MockMvc mockMvc;
  private User user;
  private UserResponse userResponse;
  private UserRequest userRequest;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @Before
  public void setup() {
    user=new User(ID,NAME,USERNAME,EMAIL,CITY_FK);
    userResponse = new UserResponse(ID, NAME, USERNAME, EMAIL, CITY_FK, REVIEWS, DATE);
    userRequest = new UserRequest(NAME, USERNAME, EMAIL, CITY_FK);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void testGetUsers() throws Exception {
    when(userService.getAllUsers()).thenReturn(Collections.singletonList(userResponse));
    mockMvc.perform(get("/users"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(ID))
           .andExpect(jsonPath("$[0].name").value(NAME))
           .andExpect(jsonPath("$[0].username").value(USERNAME))
           .andExpect(jsonPath("$[0].emailAddress").value(EMAIL))
           .andExpect(jsonPath("$[0].cityName").value(CITY_FK))
           .andExpect(jsonPath("$[0].reviews").value(REVIEWS))
           .andExpect(jsonPath("$[0].createdOn").value(DATE));
  }

  @Test
  public void testGetUserById() throws Exception {
    when(userService.getUserById(ID)).thenReturn(userResponse);
    mockMvc.perform(get("/users/"+ID))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(ID))
           .andExpect(jsonPath("$.name").value(NAME))
           .andExpect(jsonPath("$.username").value(USERNAME))
           .andExpect(jsonPath("$.emailAddress").value(EMAIL))
           .andExpect(jsonPath("$.cityName").value(CITY_FK))
           .andExpect(jsonPath("$.reviews").value(REVIEWS))
           .andExpect(jsonPath("$.createdOn").value(DATE));
  }

  @Test
  public void testGetUserByUsername() throws Exception {
    when(userService.getUserByUsername(USERNAME)).thenReturn(userResponse);
    mockMvc.perform(get("/users").param("username",USERNAME))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(ID))
           .andExpect(jsonPath("$.name").value(NAME))
           .andExpect(jsonPath("$.username").value(USERNAME))
           .andExpect(jsonPath("$.emailAddress").value(EMAIL))
           .andExpect(jsonPath("$.cityName").value(CITY_FK))
           .andExpect(jsonPath("$.reviews").value(REVIEWS))
           .andExpect(jsonPath("$.createdOn").value(DATE));

    verify(userService, times(1)).getUserByUsername(USERNAME);
  }

  @Test
  public void testGetUserByEmail() throws Exception {
    when(userService.getUserByEmailAddress(EMAIL)).thenReturn(userResponse);
    mockMvc.perform(get("/users").param("emailAddress",EMAIL))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(ID))
           .andExpect(jsonPath("$.name").value(NAME))
           .andExpect(jsonPath("$.username").value(USERNAME))
           .andExpect(jsonPath("$.emailAddress").value(EMAIL))
           .andExpect(jsonPath("$.cityName").value(CITY_FK))
           .andExpect(jsonPath("$.reviews").value(REVIEWS))
           .andExpect(jsonPath("$.createdOn").value(DATE));

    verify(userService, times(1)).getUserByEmailAddress(EMAIL);
  }
  @Test
  public void testCreateUser() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(userRequest);
    when(userService.createUser(any())).thenReturn(user);
    mockMvc.perform(post("/users")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
           .andExpect(status().isCreated())
           .andExpect(header().string("Location", "/users/1"));
  }
}
