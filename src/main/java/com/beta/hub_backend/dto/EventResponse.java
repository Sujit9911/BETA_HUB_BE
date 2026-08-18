package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDate eventDate;
    private String coordinatorName;
    private String coordinatorContact;
    private List<EventDocumentResponse> documents;
    private List<EventPhotoResponse> photos;
}