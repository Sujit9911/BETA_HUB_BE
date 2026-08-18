package com.beta.hub_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "alumni")
@Data
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Graduation year, e.g. "2022"
    @Column(nullable = false)
    private String batch;

    // e.g. "Software Development", "Embedded Systems", "Data Science"
    private String domain;

    @Column(name = "contact_number")
    private String contactNumber;

    private String email;

    private String company;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "photo_public_id")
    private String photoPublicId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}