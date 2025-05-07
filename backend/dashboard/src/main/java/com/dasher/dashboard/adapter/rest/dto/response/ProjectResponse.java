package com.dasher.dashboard.adapter.rest.dto.response;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponse {

  private UUID id;

  private String name;
}
