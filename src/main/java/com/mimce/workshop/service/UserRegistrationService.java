package com.mimce.workshop.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(InMemoryUserDetailsManager userDetailsManager,
                                   PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String username, String password) {
        String normalizedUsername = username == null ? "" : username.trim();
        String normalizedPassword = password == null ? "" : password.trim();

        if (normalizedUsername.isEmpty() || normalizedPassword.isEmpty()) {
            throw new IllegalArgumentException("Username and password are required.");
        }

        if (userDetailsManager.userExists(normalizedUsername)) {
            throw new IllegalStateException("Username already exists.");
        }

        UserDetails user = User.builder()
            .username(normalizedUsername)
            .password(passwordEncoder.encode(normalizedPassword))
            .roles("USER")
            .build();

        userDetailsManager.createUser(user);
    }
}
