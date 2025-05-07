package com.dasher.dashboard.domain.service;

import com.dasher.dashboard.domain.model.Project;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

  Project create(Project project);

  List<Project> findAll();

  Project findById(UUID id);

  Project updateProject(Project project);

  void delete( UUID projectId);
}
