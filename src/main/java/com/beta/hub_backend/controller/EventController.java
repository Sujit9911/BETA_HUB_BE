package com.beta.hub_backend.controller;

import com.beta.hub_backend.dto.EventRequest;
import com.beta.hub_backend.dto.EventResponse;
import com.beta.hub_backend.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // ---- Viewing (anyone logged in) ----

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventResponse>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(eventService.getEventsByCategory(category));
    }

    // ---- Event CRUD (ADMIN only) ----

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.createEvent(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequest request) {
        return ResponseEntity.ok(eventService.updateEvent(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) throws IOException {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Documents ----
    // Upload: any logged-in member. Delete: ADMIN only.

    @PostMapping(value = "/{id}/documents", consumes = "multipart/form-data")
    public ResponseEntity<EventResponse> addDocument(
            @PathVariable Long id,
            @RequestParam("label") String label,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(eventService.addDocument(id, label, file));
    }

    @DeleteMapping("/documents/{documentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) throws IOException {
        eventService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }

    // ---- Photos ----
    // Upload: any logged-in member. Delete: ADMIN only.

    @PostMapping(value = "/{id}/photos", consumes = "multipart/form-data")
    public ResponseEntity<EventResponse> addPhoto(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(eventService.addPhoto(id, file));
    }

    @DeleteMapping("/photos/{photoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId) throws IOException {
        eventService.deletePhoto(photoId);
        return ResponseEntity.noContent().build();
    }
}