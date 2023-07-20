package com.simonamilosheska.services;

import com.simonamilosheska.mappers.UserMapper;
import com.simonamilosheska.models.User;
import com.simonamilosheska.repositories.UserRepository;
import com.simonamilosheska.requests.UserRequest;
import com.simonamilosheska.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private UserRepository userRepository;
  private UserMapper userMapper;

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public List<UserResponse> getAllUsers() {
    return userMapper.getUserResponses(userRepository.getAllUsers());
  }

  public UserResponse getUserById(int id) {
    return userMapper.getUserResponses(userRepository.getUserById(id));
  }
  public UserResponse getUserByUsername(String username) {
    return userMapper.getUserResponses(userRepository.getUserByUsername(username));
  }

  public UserResponse getUserByEmailAddress(String emailAddress) {
    return userMapper.getUserResponses(userRepository.getUserByEmailAddress(emailAddress));
  }
  public User createUser(UserRequest userRequest){
    return userRepository.createUser(userMapper.getUser(userRequest));

  }
}
