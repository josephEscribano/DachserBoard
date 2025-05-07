package com.dasher.dashboard.adapter.rest.mapper;

import com.dasher.dashboard.adapter.rest.dto.request.PatchTaskRequest;
import com.dasher.dashboard.adapter.rest.dto.request.TaskRequest;
import com.dasher.dashboard.adapter.rest.dto.response.TaskResponse;
import com.dasher.dashboard.domain.model.Task;
import com.dasher.dashboard.domain.model.User;
import java.util.List;
import java.util.Set;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "project", ignore = true)
  @Mapping(target = "assignedTo", qualifiedByName = "mergeUsers")
  void updateTaskFromPatch(PatchTaskRequest patchTaskRequest, @MappingTarget Task task);

  @Named("mergeUsers")
  default Set<User> mergeUsers(List<User> users, @MappingTarget Set<User> mergeUsers) {
    if (users != null) {
      mergeUsers.clear();
      mergeUsers.addAll(users);
    }
    return mergeUsers;
  }

  @Mapping(target = "project.id", source = "idProject")
  Task taskRequestToTask(TaskRequest taskRequest);

  @Mapping(target = "projectId", source = "project.id")
  TaskResponse taskToTaskResponse(Task task);
}
