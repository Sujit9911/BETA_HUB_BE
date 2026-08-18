package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.AlertRequest;
import com.beta.hub_backend.dto.AlertResponse;

import java.util.List;

public interface AlertService {

    AlertResponse createAlert(
            AlertRequest request,
            String createdByEmail
    );

    List<AlertResponse> getAlertsForUser(
            String userEmail
    );

    List<AlertResponse> getAllAlertsAdmin();

    long getUnreadCount(
            String userEmail
    );

    void markAsRead(
            Long alertId,
            String userEmail
    );

    void markAllAsRead(
            String userEmail
    );

    void deleteAlert(
            Long id
    );
}