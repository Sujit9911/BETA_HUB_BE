package com.beta.hub_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Data
public class Alert {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String title;
    @Column(length = 1000, nullable = false) private String description;
    @Column(nullable = false) private String type;

    @Column(name = "event_date_time") private LocalDateTime eventDateTime;
    @Column(name = "google_meet_link") private String googleMeetLink;

    @Column(name = "send_email", nullable = false) private boolean sendEmail = false;
    @Column(nullable = false) private boolean active = true;

    @Column(name = "created_by") private String createdBy;
    @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}