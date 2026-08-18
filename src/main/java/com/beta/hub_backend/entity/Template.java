package com.beta.hub_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "templates")
@Data
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    // e.g. "Event Proposal", "Permission Letter", "Sponsorship Letter", etc.
    @Column(nullable = false)
    private String category;

    // Cloudinary file URL
    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    // Cloudinary public_id — needed later if you ever want to delete/replace the file
    @Column(name = "file_public_id")
    private String filePublicId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}