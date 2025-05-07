package com.dasher.dashboard.adapter.rest.controller;

import com.dasher.dashboard.adapter.rest.dto.request.PatchTaskRequest;
import com.dasher.dashboard.adapter.rest.dto.request.TaskRequest;
import com.dasher.dashboard.adapter.rest.dto.response.TaskResponse;
import com.dasher.dashboard.adapter.rest.mapper.TaskMapper;
import com.dasher.dashboard.domain.model.Task;
import com.dasher.dashboard.domain.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    value = "/tasks",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;

  public TaskController(TaskService taskService, TaskMapper taskMapper) {
    this.taskService = taskService;
    this.taskMapper = taskMapper;
  }

  @PostMapping
  public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest taskRequest) {

    Task task = taskService.createTask(taskMapper.taskRequestToTask(taskRequest));
    return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.taskToTaskResponse(task));
  }

  @GetMapping("{taskId}")
  public ResponseEntity<TaskResponse> findById(@PathVariable UUID taskId) {
    Task task = taskService.findById(taskId);

    return ResponseEntity.ok(taskMapper.taskToTaskResponse(task));
  }

  @GetMapping("/projectId/{projectId}")
  public ResponseEntity<List<TaskResponse>> findAllTasks(@PathVariable UUID projectId) {
    return ResponseEntity.ok(
        taskService.findTaskByProjectId(projectId).stream()
            .map(taskMapper::taskToTaskResponse)
            .toList());
  }

  @PatchMapping("/taskId/{taskId}")
  public ResponseEntity<TaskResponse> patchTask(
      @RequestBody @Valid PatchTaskRequest patchTaskRequest, @PathVariable UUID taskId) {

    Task task = taskService.findById(taskId);

    taskMapper.updateTaskFromPatch(patchTaskRequest, task);

    Task updatedTask = taskService.updateStatusTask(task);

    return ResponseEntity.ok(taskMapper.taskToTaskResponse(updatedTask));
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {

    taskService.delete(taskId);

    return ResponseEntity.noContent().build();
  }
}
