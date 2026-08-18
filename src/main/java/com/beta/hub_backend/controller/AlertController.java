package com.beta.hub_backend.controller;

import com.beta.hub_backend.dto.AlertRequest;
import com.beta.hub_backend.dto.AlertResponse;
import com.beta.hub_backend.service.AlertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<List<AlertResponse>> getMyAlerts(Authentication auth) {
        return ResponseEntity.ok(
                alertService.getAlertsForUser(auth.getName())
        );
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> unreadCount(Authentication auth) {
        return ResponseEntity.ok(
                Map.of("count", alertService.getUnreadCount(auth.getName()))
        );
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<Void> markRead(
            @PathVariable Long id,
            Authentication auth
    ) {
        alertService.markAsRead(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/read-all")
    public ResponseEntity<Void> markAllRead(Authentication auth) {
        alertService.markAllAsRead(auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN')")
    public ResponseEntity<List<AlertResponse>> getAllAdmin() {
        return ResponseEntity.ok(
                alertService.getAllAlertsAdmin()
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN')")
    public ResponseEntity<AlertResponse> create(
            @Valid @RequestBody AlertRequest request,
            Authentication auth
    ) {
        return ResponseEntity.ok(
                alertService.createAlert(request, auth.getName())
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        alertService.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }
}