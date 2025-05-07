package com.dasher.dashboard.domain.service;

import com.dasher.dashboard.domain.model.Task;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    Task createTask(Task task);

    Task findById(UUID id);

    Task updateStatusTask(Task task);

    List<Task> findTaskByProjectId(UUID projectId);
    void delete(UUID taskId);
}
