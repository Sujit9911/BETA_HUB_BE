package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.EventRequest;
import com.beta.hub_backend.dto.EventResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {

    EventResponse createEvent(EventRequest request);

    List<EventResponse> getAllEvents();

    EventResponse getEventById(Long id);

    List<EventResponse> getEventsByCategory(String category);

    EventResponse updateEvent(Long id, EventRequest request);

    void deleteEvent(Long id) throws IOException;

    EventResponse addDocument(Long eventId, String label, MultipartFile file) throws IOException;

    void deleteDocument(Long documentId) throws IOException;

    EventResponse addPhoto(Long eventId, MultipartFile file) throws IOException;

    void deletePhoto(Long photoId) throws IOException;
}