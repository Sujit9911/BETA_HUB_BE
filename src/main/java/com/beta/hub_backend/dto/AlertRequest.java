package com.beta.hub_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertRequest {
    @NotBlank private String title;
    @NotBlank private String description;
    @NotBlank private String type;
    private LocalDateTime eventDateTime;
    private String googleMeetLink;
    private boolean sendEmail;
}