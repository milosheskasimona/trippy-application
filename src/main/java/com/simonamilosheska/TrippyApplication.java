package com.simonamilosheska;

import com.simonamilosheska.controllers.CityController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrippyApplication {

  CityController cityController;

  public TrippyApplication(CityController cityController) {
    this.cityController = cityController;
  }

  public static void main(String[] args) {
    SpringApplication.run(TrippyApplication.class, args);
  }
}
