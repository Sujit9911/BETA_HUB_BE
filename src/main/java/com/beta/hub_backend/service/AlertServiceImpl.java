package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.AlertRequest;
import com.beta.hub_backend.dto.AlertResponse;
import com.beta.hub_backend.entity.Alert;
import com.beta.hub_backend.entity.AlertRecipient;
import com.beta.hub_backend.entity.User;
import com.beta.hub_backend.repo.AlertRecipientRepository;
import com.beta.hub_backend.repo.AlertRepository;
import com.beta.hub_backend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final AlertRecipientRepository recipientRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public AlertResponse createAlert(
            AlertRequest request,
            String createdByEmail
    ) {
        Alert alert = new Alert();

        alert.setTitle(request.getTitle());
        alert.setDescription(request.getDescription());
        alert.setType(request.getType());
        alert.setEventDateTime(request.getEventDateTime());
        alert.setGoogleMeetLink(request.getGoogleMeetLink());
        alert.setSendEmail(request.isSendEmail());
        alert.setCreatedBy(createdByEmail);

        Alert saved = alertRepository.save(alert);

        List<User> users = userRepository.findAll();

        for (User user : users) {
            AlertRecipient recipient = new AlertRecipient();

            recipient.setAlert(saved);
            recipient.setUser(user);

            recipientRepository.save(recipient);

            if (request.isSendEmail()) {
                emailService.sendAlertEmail(
                        user.getEmail(),
                        "BETA Alert: " + saved.getTitle(),
                        saved.getDescription()
                );
            }
        }

        return toResponse(saved, false);
    }

    @Override
    public List<AlertResponse> getAlertsForUser(
            String userEmail
    ) {
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with email: " + userEmail
                        )
                );

        return recipientRepository
                .findByUserIdOrderByAlertCreatedAtDesc(user.getId())
                .stream()
                .filter(r -> r.getAlert().isActive())
                .map(r -> toResponse(
                        r.getAlert(),
                        r.isRead()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<AlertResponse> getAllAlertsAdmin() {
        return alertRepository
                .findAllByOrderByCreatedAtDesc()
                .stream()
                .map(a -> toResponse(a, false))
                .collect(Collectors.toList());
    }

    @Override
    public long getUnreadCount(
            String userEmail
    ) {
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with email: " + userEmail
                        )
                );

        return recipientRepository
                .countByUserIdAndReadFalse(user.getId());
    }

    @Override
    public void markAsRead(
            Long alertId,
            String userEmail
    ) {
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with email: " + userEmail
                        )
                );

        recipientRepository
                .findByAlertIdAndUserId(
                        alertId,
                        user.getId()
                )
                .ifPresent(recipient -> {
                    recipient.setRead(true);
                    recipient.setReadAt(LocalDateTime.now());
                    recipientRepository.save(recipient);
                });
    }

    @Override
    public void markAllAsRead(
            String userEmail
    ) {
        User user = userRepository
                .findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with email: " + userEmail
                        )
                );

        List<AlertRecipient> unread =
                recipientRepository
                        .findByUserIdOrderByAlertCreatedAtDesc(
                                user.getId()
                        )
                        .stream()
                        .filter(r -> !r.isRead())
                        .collect(Collectors.toList());

        for (AlertRecipient recipient : unread) {
            recipient.setRead(true);
            recipient.setReadAt(LocalDateTime.now());
        }

        recipientRepository.saveAll(unread);
    }

    @Override
    public void deleteAlert(Long id) {

        Alert alert = alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alert not found with id: " + id
                        )
                );

        List<AlertRecipient> recipients =
                recipientRepository.findByAlertId(id);

        if (!recipients.isEmpty()) {
            recipientRepository.deleteAll(recipients);
        }

        alertRepository.delete(alert);
    }

    private AlertResponse toResponse(
            Alert alert,
            boolean read
    ) {
        return new AlertResponse(
                alert.getId(),
                alert.getTitle(),
                alert.getDescription(),
                alert.getType(),
                alert.getEventDateTime(),
                alert.getGoogleMeetLink(),
                alert.isSendEmail(),
                alert.isActive(),
                alert.getCreatedBy(),
                alert.getCreatedAt(),
                read
        );
    }
}