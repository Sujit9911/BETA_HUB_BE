package com.beta.hub_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "event_documents")
@Data
public class EventDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label; // e.g. "Proposal", "Permission Letter", "Report"

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_public_id")
    private String filePublicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}