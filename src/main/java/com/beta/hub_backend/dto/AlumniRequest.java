package com.beta.hub_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlumniRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Batch is required")
    private String batch;

    private String domain;

    private String contactNumber;

    @Email(message = "Invalid email format")
    private String email;

    private String company;
}