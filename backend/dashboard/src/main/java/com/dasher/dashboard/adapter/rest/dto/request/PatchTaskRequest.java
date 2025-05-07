package com.dasher.dashboard.adapter.rest.dto.request;

import com.dasher.dashboard.domain.constant.Status;
import com.dasher.dashboard.domain.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PatchTaskRequest(
    @NotNull @NotBlank String title,
    @NotNull String description,
    @NotNull Status status,
    @NotNull List<User> assignedTo) {}
