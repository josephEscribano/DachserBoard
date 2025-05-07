package com.dasher.dashboard.adapter.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectRequest(@NotBlank @NotNull String name) {}
