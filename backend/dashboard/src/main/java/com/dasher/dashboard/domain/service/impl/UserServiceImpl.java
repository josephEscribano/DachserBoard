package com.dasher.dashboard.domain.service.impl;

import com.dasher.dashboard.domain.model.User;
import com.dasher.dashboard.domain.repository.UserRepository;
import com.dasher.dashboard.domain.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> findAll() {
    LOGGER.info("Find all users");
    return userRepository.findAll();
  }
}
