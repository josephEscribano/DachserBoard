package com.dasher.dashboard.domain.service.impl;

import com.dasher.dashboard.adapter.rest.exception.ProjectIllegalArgumentException;
import com.dasher.dashboard.adapter.rest.exception.TaskExistingException;
import com.dasher.dashboard.adapter.rest.exception.TaskIllegalArgumentException;
import com.dasher.dashboard.adapter.rest.exception.TaskNotFoundException;
import com.dasher.dashboard.domain.model.Task;
import com.dasher.dashboard.domain.repository.TaskRepository;
import com.dasher.dashboard.domain.service.TaskService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

  private final TaskRepository taskRepository;

  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public Task createTask(Task task) {
    LOGGER.info("Create new task");
    taskRepository
        .findByTitle(task.getTitle())
        .ifPresent(
            existingTask -> {
              String message =
                  String.format("Task with title %s already exists", existingTask.getTitle());
              LOGGER.error(message);
              throw new TaskExistingException(message);
            });
    return taskRepository.save(task);
  }

  @Override
  public Task findById(UUID id) {
    if (Objects.isNull(id) || Strings.isBlank(String.valueOf(id))) {
      String message = "Task id can't be null or empty";
      LOGGER.error(message);
      throw new ProjectIllegalArgumentException(message);
    }
    return taskRepository
        .findById(id)
        .orElseThrow(
            () -> {
              String message = String.format("Task with id %s not found", id);
              LOGGER.error(message);
              return new TaskNotFoundException(message);
            });
  }

  @Override
  public Task updateStatusTask(Task task) {
    LOGGER.info("Task with id {} updated", task.getId());
    return taskRepository.save(task);
  }

  @Override
  public List<Task> findTaskByProjectId(UUID projectId) {

    if (Objects.isNull(projectId) || Strings.isBlank(String.valueOf(projectId))) {
      String message = "Project id can't be null or empty";
      LOGGER.error(message);
      throw new TaskIllegalArgumentException(message);
    }

    return taskRepository.findAllByProjectId(projectId);
  }

  @Override
  @Transactional
  public void delete(UUID taskId) {
    if (Objects.isNull(taskId) || Strings.isBlank(String.valueOf(taskId))) {
      String message = "Task id can't be null or empty";
      LOGGER.error(message);
      throw new ProjectIllegalArgumentException(message);
    }
    taskRepository
        .findById(taskId)
        .orElseThrow(
            () -> {
              String message = String.format("Task with id %s not found", taskId);
              LOGGER.error(message);
              return new TaskNotFoundException(message);
            });

    LOGGER.info("Task with id {} deleted", taskId);
    taskRepository.deleteById(taskId);
  }
}
