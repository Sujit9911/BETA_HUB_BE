package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.AuthResponse;
import com.beta.hub_backend.dto.LoginRequest;
import com.beta.hub_backend.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}