package com.dasher.dashboard.domain.service;

import com.dasher.dashboard.domain.model.User;
import java.util.List;

public interface UserService {

  List<User> findAll();
}
