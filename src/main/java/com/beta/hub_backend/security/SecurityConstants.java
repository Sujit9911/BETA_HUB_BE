package com.beta.hub_backend.security;

public class SecurityConstants {

    // JWT secret key (min 256 bits for HS256) — move to application.yml in real deployment
    public static final String JWT_SECRET = "beta-hub-super-secret-key-change-this-in-production-min-32-chars";

    // Token validity: 24 hours (in milliseconds)
    public static final long JWT_EXPIRATION = 86400000;

    // Header + prefix used for sending the token
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}