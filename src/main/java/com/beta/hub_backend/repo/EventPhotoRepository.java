package com.beta.hub_backend.repo;

import com.beta.hub_backend.entity.EventPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventPhotoRepository extends JpaRepository<EventPhoto, Long> {

    List<EventPhoto> findByEventId(Long eventId);
}