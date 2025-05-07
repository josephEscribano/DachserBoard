package com.dasher.dashboard.adapter.rest.dto.response;

import com.dasher.dashboard.domain.constant.Status;
import com.dasher.dashboard.domain.model.User;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {

  private UUID id;

  private UUID projectId;

  private String title;

  private String description;

  private Status status;

  private List<User> assignedTo;
}
