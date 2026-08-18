package com.beta.hub_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamMemberRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Designation is required")
    private String designation;

    private String branch;

    private String year;

    private String passingYear;

    @NotBlank(message = "Academic year label is required")
    private String academicYearLabel;
}