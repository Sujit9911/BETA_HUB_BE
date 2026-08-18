package com.beta.hub_backend.repo;

import com.beta.hub_backend.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	List<Notice> findByTitleContainingIgnoreCase(String title);
    List<Notice> findByPinnedTrueOrderByCreatedAtDesc();

    List<Notice> findAllByOrderByCreatedAtDesc();

    List<Notice> findByType(String type);
}