package com.dasher.dashboard.adapter.rest.mapper;

import com.dasher.dashboard.adapter.rest.dto.request.ProjectRequest;
import com.dasher.dashboard.adapter.rest.dto.response.ProjectResponse;
import com.dasher.dashboard.domain.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

  Project projectRequestToProject(ProjectRequest projectRequest);

  ProjectResponse projectToProjectResponse(Project project);
}
