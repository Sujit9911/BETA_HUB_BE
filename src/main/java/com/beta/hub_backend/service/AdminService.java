package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.AdminUserResponse;

import java.util.List;

public interface AdminService {

    List<AdminUserResponse> getAdmins();

    List<AdminUserResponse> getMembers();

    void makeAdmin(Long userId);

    void removeAdmin(Long userId);

    void deleteUser(Long userId, String currentAdminEmail);
}