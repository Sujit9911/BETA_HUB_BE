package com.beta.hub_backend.repo;

import com.beta.hub_backend.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByActiveTrueOrderByCreatedAtDesc();
    List<Alert> findAllByOrderByCreatedAtDesc();
}