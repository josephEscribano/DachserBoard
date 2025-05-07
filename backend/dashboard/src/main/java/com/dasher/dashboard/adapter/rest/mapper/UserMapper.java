package com.dasher.dashboard.adapter.rest.mapper;

import com.dasher.dashboard.adapter.rest.dto.request.UserRequest;
import com.dasher.dashboard.adapter.rest.dto.response.UserResponse;
import com.dasher.dashboard.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponse userToUserResponse(User user);
}
