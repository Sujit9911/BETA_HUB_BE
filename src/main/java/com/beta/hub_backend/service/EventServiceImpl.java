package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.*;
import com.beta.hub_backend.entity.Event;
import com.beta.hub_backend.entity.EventDocument;
import com.beta.hub_backend.entity.EventPhoto;
import com.beta.hub_backend.repo.EventDocumentRepository;
import com.beta.hub_backend.repo.EventPhotoRepository;
import com.beta.hub_backend.repo.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventDocumentRepository eventDocumentRepository;
    private final EventPhotoRepository eventPhotoRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event event = new Event();
        applyRequest(event, request);
        Event saved = eventRepository.save(event);
        return toResponse(saved);
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAllByOrderByEventDateDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse getEventById(Long id) {
        Event event = findEventOrThrow(id);
        return toResponse(event);
    }

    @Override
    public List<EventResponse> getEventsByCategory(String category) {
        return eventRepository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = findEventOrThrow(id);
        applyRequest(event, request);
        Event updated = eventRepository.save(event);
        return toResponse(updated);
    }

    @Override
    public void deleteEvent(Long id) throws IOException {
        Event event = findEventOrThrow(id);

        // Clean up all associated Cloudinary files before deleting the event
        for (EventDocument doc : event.getDocuments()) {
            if (doc.getFilePublicId() != null) {
                cloudinaryService.deleteFile(doc.getFilePublicId());
            }
        }
        for (EventPhoto photo : event.getPhotos()) {
            if (photo.getPhotoPublicId() != null) {
                cloudinaryService.deleteFile(photo.getPhotoPublicId());
            }
        }

        eventRepository.delete(event); // cascade removes documents/photos rows
    }

    @Override
    public EventResponse addDocument(Long eventId, String label, MultipartFile file) throws IOException {
        Event event = findEventOrThrow(eventId);

        Map<String, String> uploadResult = cloudinaryService.uploadFile(file, "events/documents");

        EventDocument document = new EventDocument();
        document.setLabel(label);
        document.setFileUrl(uploadResult.get("url"));
        document.setFilePublicId(uploadResult.get("publicId"));
        document.setEvent(event);

        eventDocumentRepository.save(document);

        Event refreshed = findEventOrThrow(eventId);
        return toResponse(refreshed);
    }

    @Override
    public void deleteDocument(Long documentId) throws IOException {
        EventDocument document = eventDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found with id: " + documentId));

        if (document.getFilePublicId() != null) {
            cloudinaryService.deleteFile(document.getFilePublicId());
        }
        eventDocumentRepository.delete(document);
    }

    @Override
    public EventResponse addPhoto(Long eventId, MultipartFile file) throws IOException {
        Event event = findEventOrThrow(eventId);

        Map<String, String> uploadResult = cloudinaryService.uploadFile(file, "events/photos");

        EventPhoto photo = new EventPhoto();
        photo.setPhotoUrl(uploadResult.get("url"));
        photo.setPhotoPublicId(uploadResult.get("publicId"));
        photo.setEvent(event);

        eventPhotoRepository.save(photo);

        Event refreshed = findEventOrThrow(eventId);
        return toResponse(refreshed);
    }

    @Override
    public void deletePhoto(Long photoId) throws IOException {
        EventPhoto photo = eventPhotoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found with id: " + photoId));

        if (photo.getPhotoPublicId() != null) {
            cloudinaryService.deleteFile(photo.getPhotoPublicId());
        }
        eventPhotoRepository.delete(photo);
    }

    // ---- helpers ----

    private Event findEventOrThrow(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    private void applyRequest(Event event, EventRequest request) {
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setEventDate(request.getEventDate());
        event.setCoordinatorName(request.getCoordinatorName());
        event.setCoordinatorContact(request.getCoordinatorContact());
    }

    private EventResponse toResponse(Event event) {
        List<EventDocumentResponse> docs = event.getDocuments() == null ? List.of() :
                event.getDocuments().stream()
                        .map(d -> new EventDocumentResponse(d.getId(), d.getLabel(), d.getFileUrl()))
                        .collect(Collectors.toList());

        List<EventPhotoResponse> photos = event.getPhotos() == null ? List.of() :
                event.getPhotos().stream()
                        .map(p -> new EventPhotoResponse(p.getId(), p.getPhotoUrl()))
                        .collect(Collectors.toList());

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getCategory(),
                event.getEventDate(),
                event.getCoordinatorName(),
                event.getCoordinatorContact(),
                docs,
                photos
        );
    }
}