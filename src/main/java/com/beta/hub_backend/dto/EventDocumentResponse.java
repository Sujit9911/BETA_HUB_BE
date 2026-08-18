package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDocumentResponse {

    private Long id;
    private String label;
    private String fileUrl;
}