package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String fileUrl;
    private LocalDateTime createdAt;
}