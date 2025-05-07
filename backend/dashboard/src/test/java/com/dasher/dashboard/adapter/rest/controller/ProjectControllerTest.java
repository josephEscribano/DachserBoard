package com.dasher.dashboard.adapter.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dasher.dashboard.adapter.rest.dto.request.ProjectRequest;
import com.dasher.dashboard.adapter.rest.dto.response.ProjectResponse;
import com.dasher.dashboard.adapter.rest.exception.ProjectNotFoundException;
import com.dasher.dashboard.adapter.rest.mapper.ProjectMapper;
import com.dasher.dashboard.domain.model.Project;
import com.dasher.dashboard.domain.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProjectController.class)
public class ProjectControllerTest {

  public static final String TEST_PROJECT = "Test Project";
  private static final String BASE_URL = "/projects";
  private static final UUID PROJECT_ID = UUID.randomUUID();
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private ProjectService projectService;

  @MockitoBean private ProjectMapper projectMapper;

  private Project project;
  private ProjectResponse projectResponse;

  @BeforeEach
  void setUp() {
    project = new Project();
    project.setId(PROJECT_ID);
    project.setName(TEST_PROJECT);

    projectResponse = new ProjectResponse();
    projectResponse.setId(PROJECT_ID);
    projectResponse.setName(TEST_PROJECT);
  }

  @Nested
  class createProject {

    @Test
    void success() throws Exception {
      ProjectRequest projectRequest = new ProjectRequest(TEST_PROJECT);

      Mockito.when(projectMapper.projectRequestToProject(projectRequest)).thenReturn(project);
      Mockito.when(projectService.create(project)).thenReturn(project);
      Mockito.when(projectMapper.projectToProjectResponse(project)).thenReturn(projectResponse);
      String jsonRequest = objectMapper.writeValueAsString(projectRequest);

      mockMvc
          .perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").value(PROJECT_ID.toString()))
          .andExpect(jsonPath("$.name").value(TEST_PROJECT));
    }

    @Test
    void shouldReturnBadRequest() throws Exception {
      ProjectRequest projectRequest = new ProjectRequest("");

      String jsonRequest = objectMapper.writeValueAsString(projectRequest);
      mockMvc
          .perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  class findAllProjects {

    @Test
    void success() throws Exception {
      Mockito.when(projectService.findAll()).thenReturn(List.of(project));
      Mockito.when(projectMapper.projectToProjectResponse(project)).thenReturn(projectResponse);
      mockMvc
          .perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].id").value(PROJECT_ID.toString()))
          .andExpect(jsonPath("$[0].name").value(TEST_PROJECT));
    }
  }

  @Nested
  class findProjectById {

    @Test
    void success() throws Exception {
      Mockito.when(projectService.findById(PROJECT_ID)).thenReturn(project);
      Mockito.when(projectMapper.projectToProjectResponse(project)).thenReturn(projectResponse);
      mockMvc
          .perform(
              get(String.format("%s/%s", BASE_URL, PROJECT_ID))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(PROJECT_ID.toString()))
          .andExpect(jsonPath("$.name").value(TEST_PROJECT));
    }

    @Test
    void shouldReturnNotFound() throws Exception {
      Mockito.when(projectService.findById(PROJECT_ID))
          .thenThrow(
              new ProjectNotFoundException(
                  String.format("Project with id %s not found", PROJECT_ID)));

      mockMvc
          .perform(
              get(String.format("%s/%s", BASE_URL, PROJECT_ID))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    }
  }

  @Nested
  class updateProject {
    public static final String TEST_PROJECT_TEST = "Test project test";
    private static final UUID PROJECT_UPDATED_ID = UUID.randomUUID();
    private Project projectUpdated;
    private ProjectResponse projectUpdateResponse;

    @BeforeEach
    void setUp() {
      projectUpdated = new Project();
      projectUpdated.setId(PROJECT_UPDATED_ID);
      projectUpdated.setName(TEST_PROJECT_TEST);

      projectUpdateResponse = new ProjectResponse();
      projectUpdateResponse.setId(PROJECT_UPDATED_ID);
      projectUpdateResponse.setName(TEST_PROJECT_TEST);
    }

    @Test
    void success() throws Exception {
      Mockito.when(projectService.findById(PROJECT_ID)).thenReturn(project);
      Mockito.when(projectService.updateProject(project)).thenReturn(projectUpdated);
      Mockito.when(projectMapper.projectToProjectResponse(projectUpdated))
          .thenReturn(projectUpdateResponse);

      mockMvc
          .perform(
              patch(String.format("%s/%s/%s", BASE_URL, "projectId", PROJECT_ID))
                  .queryParam("name", TEST_PROJECT_TEST)
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(PROJECT_UPDATED_ID.toString()))
          .andExpect(jsonPath("$.name").value(TEST_PROJECT_TEST));
    }

    @Test
    void shouldReturnNotFound() throws Exception {
      Mockito.when(projectService.findById(PROJECT_ID))
          .thenThrow(
              new ProjectNotFoundException(
                  String.format("Project with id %s not found", PROJECT_ID)));

      mockMvc
          .perform(
              patch(String.format("%s/%s/%s", BASE_URL, "projectId", PROJECT_ID))
                  .queryParam("name", TEST_PROJECT_TEST)
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequest() throws Exception {

      mockMvc
          .perform(
              patch(String.format("%s/%s/%s", BASE_URL, "projectId", PROJECT_ID))
                  .queryParam("name", "")
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  class deleteProject {

    @Test
    void success() throws Exception {
      Mockito.doNothing().when(projectService).delete(PROJECT_ID);

      mockMvc
          .perform(
              delete(String.format("%s/%s", BASE_URL, PROJECT_ID))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
    }
  }
}
