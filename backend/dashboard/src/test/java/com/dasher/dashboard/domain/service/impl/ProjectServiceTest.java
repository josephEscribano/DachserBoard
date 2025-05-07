package com.dasher.dashboard.domain.service.impl;

import com.dasher.dashboard.adapter.rest.exception.ProjectExistingException;
import com.dasher.dashboard.adapter.rest.exception.ProjectIllegalArgumentException;
import com.dasher.dashboard.adapter.rest.exception.ProjectNotFoundException;
import com.dasher.dashboard.domain.model.Project;
import com.dasher.dashboard.domain.repository.ProjectRepository;
import com.dasher.dashboard.domain.service.ProjectService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class ProjectServiceTest {

  public static final String TEST_PROJECT = "Test Project";
  private static final UUID PROJECT_ID = UUID.randomUUID();

  @Autowired private ProjectService projectService;
  @MockitoBean private ProjectRepository projectRepository;

  private Project project;

  @BeforeEach
  void setUp() {
    project = new Project();
    project.setId(PROJECT_ID);
    project.setName(TEST_PROJECT);
  }

  @Nested
  class createProject {

    @Test
    void success() {
      Mockito.when(projectRepository.save(project)).thenReturn(project);

      Project projectResult = projectService.create(project);

      Assertions.assertEquals(project.getId(), projectResult.getId());
      Assertions.assertEquals(project.getName(), projectResult.getName());
    }

    @Test
    void shouldReturnErrorWhenProjectAlreadyExists() {
      Mockito.when(projectRepository.findProjectByName(TEST_PROJECT))
          .thenReturn(Optional.of(project));

      Assertions.assertThrows(ProjectExistingException.class, () -> projectService.create(project));
    }
  }

  @Nested
  class findAll {

    @Test
    void success() {
      Mockito.when(projectRepository.findAll()).thenReturn(List.of(project));

      List<Project> result = projectService.findAll();

      Assertions.assertEquals(project.getId(), result.get(0).getId());
      Assertions.assertEquals(project.getName(), result.get(0).getName());
    }
  }

  @Nested
  class findById {

    @Test
    void success() {
      Mockito.when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));

      Project result = projectService.findById(PROJECT_ID);

      Assertions.assertEquals(project.getId(), result.getId());
      Assertions.assertEquals(project.getName(), result.getName());
    }

    @Test
    void shouldReturnErrorProjectNotFound() {
      Mockito.when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

      Assertions.assertThrows(
          ProjectNotFoundException.class, () -> projectService.findById(PROJECT_ID));
    }

    @Test
    void shouldReturnErrorIdIsNull() {

      Assertions.assertThrows(
          ProjectIllegalArgumentException.class, () -> projectService.findById(null));
    }
  }

  @Nested
  class updateProject {

    @Test
    void success() {
      project.setName(TEST_PROJECT);
      Mockito.when(projectRepository.save(project)).thenReturn(project);

      Project result = projectService.updateProject(project);

      Assertions.assertEquals(project.getId(), result.getId());
      Assertions.assertEquals(project.getName(), result.getName());
    }
  }

  @Nested
  class deleteProject {

    @Test
    void success() {
      Mockito.when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));
      Mockito.doNothing().when(projectRepository).deleteById(PROJECT_ID);

      Assertions.assertDoesNotThrow(
          () -> projectService.delete(PROJECT_ID), "No exception should be thrown");
    }

    @Test
    void shouldReturnErrorProjectNotFound() {
      Mockito.when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

      Assertions.assertThrows(
          ProjectNotFoundException.class, () -> projectService.delete(PROJECT_ID));
    }

    @Test
    void shouldReturnErrorIdIsNull() {

      Assertions.assertThrows(
          ProjectIllegalArgumentException.class, () -> projectService.delete(null));
    }
  }
}
