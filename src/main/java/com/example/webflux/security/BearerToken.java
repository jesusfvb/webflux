package com.example.webflux.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class BearerToken extends AbstractAuthenticationToken {

    private final String token;

    public BearerToken(String token) {
        super(null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }
}
