package com.beta.hub_backend.controller;

import com.beta.hub_backend.dto.TeamMemberRequest;
import com.beta.hub_backend.dto.TeamMemberResponse;
import com.beta.hub_backend.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/years")
    public ResponseEntity<List<String>> getAllYears() {
        return ResponseEntity.ok(teamService.getAllYearLabels());
    }

    @GetMapping("/year/{yearLabel}")
    public ResponseEntity<List<TeamMemberResponse>> getMembersByYear(
            @PathVariable String yearLabel
    ) {
        return ResponseEntity.ok(
                teamService.getMembersByYear(yearLabel)
        );
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamMemberResponse> addMember(
            @Valid @RequestPart("data") TeamMemberRequest request,
            @RequestPart(value = "photo", required = false)
            MultipartFile photo
    ) throws IOException {

        return ResponseEntity.ok(
                teamService.addMember(request, photo)
        );
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamMemberResponse> updateMember(
            @PathVariable Long id,
            @Valid @RequestPart("data") TeamMemberRequest request,
            @RequestPart(value = "photo", required = false)
            MultipartFile photo
    ) throws IOException {

        return ResponseEntity.ok(
                teamService.updateMember(id, request, photo)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long id
    ) throws IOException {

        teamService.deleteMember(id);

        return ResponseEntity.noContent().build();
    }
}