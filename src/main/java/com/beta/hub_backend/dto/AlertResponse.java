package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class AlertResponse {
    private Long id;
    private String title;
    private String description;
    private String type;
    private LocalDateTime eventDateTime;
    private String googleMeetLink;
    private boolean sendEmail;
    private boolean active;
    private String createdBy;
    private LocalDateTime createdAt;
    private boolean read;
}