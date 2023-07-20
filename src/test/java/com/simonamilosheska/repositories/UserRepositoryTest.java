package com.simonamilosheska.repositories;

import com.simonamilosheska.mappers.UserMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

  @Mock
  private UserMapper userMapper;

  @Mock
  private HikariDataSource dataSource;

  @Mock
  private Connection connection;

  @Mock
  private PreparedStatement preparedStatement;

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  private UserRepository userRepository;

  @Before
  public void setupConnection() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
  }
  @After
  public void verifyConnectionClosed() throws SQLException {
    verify(connection, times(1)).close();
  }
  @Test(expected = RuntimeException.class)
  public void testGetAllUsers_exception() throws SQLException {
    when(connection.createStatement()).thenThrow(SQLException.class);
    userRepository.getAllUsers();
  }
}
