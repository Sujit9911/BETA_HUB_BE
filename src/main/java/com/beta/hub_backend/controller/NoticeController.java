package com.beta.hub_backend.controller;

import com.beta.hub_backend.dto.NoticeRequest;
import com.beta.hub_backend.dto.NoticeResponse;
import com.beta.hub_backend.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // Anyone logged in can view
    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @GetMapping("/pinned")
    public ResponseEntity<List<NoticeResponse>> getPinnedNotices() {
        return ResponseEntity.ok(noticeService.getPinnedNotices());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<NoticeResponse>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(noticeService.getNoticesByType(type));
    }

    // Only ADMIN can create/update/delete
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> createNotice(@Valid @RequestBody NoticeRequest request) {
        return ResponseEntity.ok(noticeService.createNotice(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeResponse> updateNotice(
            @PathVariable Long id,
            @Valid @RequestBody NoticeRequest request) {
        return ResponseEntity.ok(noticeService.updateNotice(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}