package com.beta.hub_backend.dto;

import com.beta.hub_backend.entity.Role;

public record AdminUserResponse(
        Long id,
        String name,
        String email,
        String prnNumber,
        String branch,
        String year,
        Role role
) {
}