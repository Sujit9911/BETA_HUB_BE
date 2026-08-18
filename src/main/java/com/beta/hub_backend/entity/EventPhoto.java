package com.beta.hub_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "event_photos")
@Data
public class EventPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "photo_public_id")
    private String photoPublicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}