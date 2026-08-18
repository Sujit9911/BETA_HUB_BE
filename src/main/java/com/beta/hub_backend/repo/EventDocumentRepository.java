package com.beta.hub_backend.repo;

import com.beta.hub_backend.entity.EventDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventDocumentRepository extends JpaRepository<EventDocument, Long> {

    List<EventDocument> findByEventId(Long eventId);
}