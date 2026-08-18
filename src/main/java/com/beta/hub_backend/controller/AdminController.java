package com.beta.hub_backend.controller;

import com.beta.hub_backend.dto.AdminUserResponse;
import com.beta.hub_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admins")
    public ResponseEntity<List<AdminUserResponse>> getAdmins() {
        return ResponseEntity.ok(adminService.getAdmins());
    }

    @GetMapping("/members")
    public ResponseEntity<List<AdminUserResponse>> getMembers() {
        return ResponseEntity.ok(adminService.getMembers());
    }

    @PutMapping("/users/{id}/make-admin")
    public ResponseEntity<String> makeAdmin(@PathVariable Long id) {
        adminService.makeAdmin(id);
        return ResponseEntity.ok("User promoted to admin");
    }

    @PutMapping("/users/{id}/remove-admin")
    public ResponseEntity<String> removeAdmin(@PathVariable Long id) {
        adminService.removeAdmin(id);
        return ResponseEntity.ok("Admin privileges removed");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            Authentication authentication
    ) {
        adminService.deleteUser(id, authentication.getName());
        return ResponseEntity.ok("User account deleted");
    }
}