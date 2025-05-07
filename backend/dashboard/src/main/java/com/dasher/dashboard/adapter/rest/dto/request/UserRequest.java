package com.dasher.dashboard.adapter.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @NotNull @NotBlank String name, @NotNull @NotBlank String email, String photoUrl) {}
