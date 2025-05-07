package com.dasher.dashboard.adapter.rest.dto.request;

import com.dasher.dashboard.domain.constant.Status;
import com.dasher.dashboard.domain.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

public record TaskRequest(
    @NotNull UUID idProject,
    @NotBlank @NotNull String title,
    String description,
    Status status,
    @NotNull Set<User> assignedTo) {}
