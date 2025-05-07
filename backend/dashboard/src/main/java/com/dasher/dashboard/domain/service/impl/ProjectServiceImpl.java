package com.dasher.dashboard.domain.service.impl;

import com.dasher.dashboard.adapter.rest.exception.ProjectExistingException;
import com.dasher.dashboard.adapter.rest.exception.ProjectIllegalArgumentException;
import com.dasher.dashboard.adapter.rest.exception.ProjectNotFoundException;
import com.dasher.dashboard.domain.model.Project;
import com.dasher.dashboard.domain.repository.ProjectRepository;
import com.dasher.dashboard.domain.service.ProjectService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

  private final ProjectRepository projectRepository;

  public ProjectServiceImpl(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public Project create(Project project) {
    LOGGER.info("Create a new project");
    projectRepository
        .findProjectByName(project.getName())
        .ifPresent(
            existingProject -> {
              String message =
                  String.format("Project with name %s already exists", project.getName());
              LOGGER.error(message);
              throw new ProjectExistingException(message);
            });

    return projectRepository.save(project);
  }

  @Override
  public List<Project> findAll() {
    LOGGER.info("Find all projects");
    return projectRepository.findAll();
  }

  @Override
  public Project findById(UUID id) {
    LOGGER.info("Find project by id");
    if (Objects.isNull(id) || Strings.isBlank(String.valueOf(id))) {
      String message = "Project id can't be null or empty";
      LOGGER.error(message);
      throw new ProjectIllegalArgumentException(message);
    }
    return projectRepository
        .findById(id)
        .orElseThrow(
            () -> {
              String message = String.format("Project with id %s not found", id);
              LOGGER.error(message);
              return new ProjectNotFoundException(message);
            });
  }

  @Override
  public Project updateProject(Project project) {
    return projectRepository.save(project);
  }

  @Override
  @Transactional
  public void delete(UUID projectId) {
    if (Objects.isNull(projectId) || Strings.isBlank(String.valueOf(projectId))) {
      String message = "Project id can't be null or empty";
      LOGGER.error(message);
      throw new ProjectIllegalArgumentException(message);
    }
    projectRepository
        .findById(projectId)
        .orElseThrow(
            () -> {
              String message = String.format("Project with id %s not found", projectId);
              LOGGER.error(message);
              return new ProjectNotFoundException(message);
            });
    projectRepository.deleteById(projectId);
  }
}
