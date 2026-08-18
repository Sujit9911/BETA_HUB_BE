package com.beta.hub_backend.controller;

import com.beta.hub_backend.dto.AlumniRequest;
import com.beta.hub_backend.dto.AlumniResponse;
import com.beta.hub_backend.service.AlumniService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alumni")
@RequiredArgsConstructor
public class AlumniController {

    private final AlumniService alumniService;

    // Anyone logged in can view
    @GetMapping
    public ResponseEntity<List<AlumniResponse>> getAllAlumni() {
        return ResponseEntity.ok(alumniService.getAllAlumni());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumniResponse> getAlumniById(@PathVariable Long id) {
        return ResponseEntity.ok(alumniService.getAlumniById(id));
    }

    @GetMapping("/filter/batch/{batch}")
    public ResponseEntity<List<AlumniResponse>> filterByBatch(@PathVariable String batch) {
        return ResponseEntity.ok(alumniService.filterByBatch(batch));
    }

    @GetMapping("/filter/domain/{domain}")
    public ResponseEntity<List<AlumniResponse>> filterByDomain(@PathVariable String domain) {
        return ResponseEntity.ok(alumniService.filterByDomain(domain));
    }

    @GetMapping("/filter/company/{company}")
    public ResponseEntity<List<AlumniResponse>> filterByCompany(@PathVariable String company) {
        return ResponseEntity.ok(alumniService.filterByCompany(company));
    }

    // Only ADMIN can add/update/delete
    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlumniResponse> addAlumni(
            @Valid @RequestPart("data") AlumniRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        return ResponseEntity.ok(alumniService.addAlumni(request, photo));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlumniResponse> updateAlumni(
            @PathVariable Long id,
            @Valid @RequestPart("data") AlumniRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        return ResponseEntity.ok(alumniService.updateAlumni(id, request, photo));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAlumni(@PathVariable Long id) throws IOException {
        alumniService.deleteAlumni(id);
        return ResponseEntity.noContent().build();
    }
}