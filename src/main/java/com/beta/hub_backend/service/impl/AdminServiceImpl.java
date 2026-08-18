package com.beta.hub_backend.service.impl;

import com.beta.hub_backend.dto.AdminUserResponse;
import com.beta.hub_backend.entity.Role;
import com.beta.hub_backend.entity.User;
import com.beta.hub_backend.repo.AlertRecipientRepository;
import com.beta.hub_backend.repo.UserRepository;
import com.beta.hub_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AlertRecipientRepository alertRecipientRepository;

    @Override
    public List<AdminUserResponse> getAdmins() {
        return userRepository.findByRole(Role.ADMIN)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<AdminUserResponse> getMembers() {
        return userRepository.findByRole(Role.MEMBER)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void makeAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("User is already an admin");
        }

        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    @Override
    public void removeAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("User is not an admin");
        }

        long adminCount = userRepository.countByRole(Role.ADMIN);

        if (adminCount <= 1) {
            throw new RuntimeException("Cannot remove the last administrator");
        }

        user.setRole(Role.MEMBER);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId, String currentAdminEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getEmail().equalsIgnoreCase(currentAdminEmail)) {
            throw new RuntimeException("You cannot delete your own account");
        }

        if (user.getRole() == Role.ADMIN) {
            long adminCount = userRepository.countByRole(Role.ADMIN);

            if (adminCount <= 1) {
                throw new RuntimeException("Cannot delete the last administrator");
            }
        }

        alertRecipientRepository.deleteByUserId(userId);
        userRepository.delete(user);
    }

    private AdminUserResponse toResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPrnNumber(),
                user.getBranch(),
                user.getYear(),
                user.getRole()
        );
    }
}