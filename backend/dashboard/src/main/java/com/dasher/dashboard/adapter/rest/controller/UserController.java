package com.dasher.dashboard.adapter.rest.controller;

import com.dasher.dashboard.adapter.rest.dto.response.UserResponse;
import com.dasher.dashboard.adapter.rest.mapper.UserMapper;
import com.dasher.dashboard.domain.model.User;
import com.dasher.dashboard.domain.service.UserService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "/users",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> findAll() {
    List<User> users = userService.findAll();

    return ResponseEntity.ok(users.stream().map(userMapper::userToUserResponse).toList());
  }
}
