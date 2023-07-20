package com.simonamilosheska.controllers;

import com.simonamilosheska.models.City;
import com.simonamilosheska.responses.CityResponse;
import com.simonamilosheska.services.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.simonamilosheska.constants.CityConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class CityControllerTest {



  private MockMvc mockMvc;
  private City city;
  private CityResponse cityResponse;
  @Mock
  private CityService cityService;

  @InjectMocks
  private CityController cityController;

  @Before
  public void setup() {
    city = new City();
    cityResponse = new CityResponse(CITY_ID, CITY_NAME);
    mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
  }

  @Test
  public void testGetCities() throws Exception {
    when(cityService.getAllCities()).thenReturn(Collections.singletonList(cityResponse));
    mockMvc.perform(get("/cities"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(CITY_ID))
           .andExpect(jsonPath("$[0].name").value(CITY_NAME));
  }
}