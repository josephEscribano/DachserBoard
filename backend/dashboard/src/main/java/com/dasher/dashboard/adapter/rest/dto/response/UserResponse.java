package com.dasher.dashboard.adapter.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class UserResponse {

  private UUID id;

  private String name;

  private String email;

  private String photoUrl;
}
