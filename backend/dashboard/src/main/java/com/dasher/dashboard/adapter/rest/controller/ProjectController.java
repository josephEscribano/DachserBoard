package com.dasher.dashboard.adapter.rest.controller;

import com.dasher.dashboard.adapter.rest.dto.request.ProjectRequest;
import com.dasher.dashboard.adapter.rest.dto.response.ProjectResponse;
import com.dasher.dashboard.adapter.rest.mapper.ProjectMapper;
import com.dasher.dashboard.domain.model.Project;
import com.dasher.dashboard.domain.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    value = "/projects",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

  private final ProjectMapper projectMapper;
  private final ProjectService projectService;

  public ProjectController(ProjectMapper projectMapper, ProjectService projectService) {
    this.projectMapper = projectMapper;
    this.projectService = projectService;
  }

  @PostMapping
  public ResponseEntity<ProjectResponse> createProject(
      @RequestBody @Valid ProjectRequest projectRequest) {

    Project project = projectMapper.projectRequestToProject(projectRequest);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(projectMapper.projectToProjectResponse(projectService.create(project)));
  }

  @GetMapping
  public ResponseEntity<List<ProjectResponse>> findAll() {
    List<Project> projects = projectService.findAll();

    return ResponseEntity.ok(
        projects.stream().map(projectMapper::projectToProjectResponse).toList());
  }

  @GetMapping("{projectId}")
  public ResponseEntity<ProjectResponse> findById(@PathVariable UUID projectId) {
    Project project = projectService.findById(projectId);

    return ResponseEntity.ok(projectMapper.projectToProjectResponse(project));
  }

  @PatchMapping("/projectId/{projectId}")
  public ResponseEntity<ProjectResponse> updateProject(
      @RequestParam @NotNull @NotBlank String name, @PathVariable UUID projectId) {
    Project project = projectService.findById(projectId);

    project.setName(name);

    return ResponseEntity.ok(
        projectMapper.projectToProjectResponse(projectService.updateProject(project)));
  }

  @DeleteMapping("/{projectId}")
  public ResponseEntity<ProjectResponse> deleteProject(@PathVariable UUID projectId) {

    projectService.delete(projectId);

    return ResponseEntity.noContent().build();
  }
}
