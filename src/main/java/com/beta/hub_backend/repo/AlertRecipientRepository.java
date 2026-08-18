package com.beta.hub_backend.repo;

import com.beta.hub_backend.entity.AlertRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRecipientRepository
        extends JpaRepository<AlertRecipient, Long> {

    List<AlertRecipient> findByUserIdOrderByAlertCreatedAtDesc(
            Long userId
    );

    Optional<AlertRecipient> findByAlertIdAndUserId(
            Long alertId,
            Long userId
    );

    long countByUserIdAndReadFalse(
            Long userId
    );

    List<AlertRecipient> findByAlertId(
            Long alertId
    );

    void deleteByUserId(Long userId);
}