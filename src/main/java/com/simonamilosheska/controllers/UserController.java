package com.simonamilosheska.controllers;

import com.simonamilosheska.models.User;
import com.simonamilosheska.requests.UserRequest;
import com.simonamilosheska.responses.UserResponse;
import com.simonamilosheska.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponse>> getUsers() {
    List<UserResponse> userResponse = userService.getAllUsers();
    return ResponseEntity.ok(userResponse);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
    UserResponse userResponse = userService.getUserById(id);
    return ResponseEntity.ok(userResponse);
  }

  @GetMapping(value = "/users", params = "username")
  public ResponseEntity<UserResponse> getUserByUsername(@RequestParam("username")  String username) {
    UserResponse userResponse = userService.getUserByUsername(username);
    return ResponseEntity.ok(userResponse);
  }

  @GetMapping(value = "/users", params = "emailAddress")
  public ResponseEntity<UserResponse> getUserByEmailAddress(@RequestParam("emailAddress")  String emailAddress) {
    UserResponse userResponse = userService.getUserByEmailAddress(emailAddress);
    return ResponseEntity.ok(userResponse);
  }

  @PostMapping("/users")
  public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequest userRequest) {
    User user = userService.createUser(userRequest);

    URI location = UriComponentsBuilder.fromUriString("/users/{id}")
                                       .buildAndExpand(user.getId())
                                       .toUri();

    return ResponseEntity.created(location).build();
  }
}
