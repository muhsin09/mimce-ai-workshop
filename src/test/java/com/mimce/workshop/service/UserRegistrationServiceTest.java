package com.mimce.workshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRegistrationServiceTest {

    private InMemoryUserDetailsManager userDetailsManager;
    private UserRegistrationService userRegistrationService;

    @BeforeEach
    void setUp() {
        userDetailsManager = new InMemoryUserDetailsManager();
        userRegistrationService = new UserRegistrationService(userDetailsManager, new BCryptPasswordEncoder());
    }

    @Test
    void registerCreatesNewUser() {
        userRegistrationService.register("new-user", "secret123");

        assertThat(userDetailsManager.userExists("new-user")).isTrue();
    }

    @Test
    void registerTrimsUsername() {
        userRegistrationService.register("  trim-user  ", "secret123");

        assertThat(userDetailsManager.userExists("trim-user")).isTrue();
    }

    @Test
    void registerRejectsDuplicateUsername() {
        userRegistrationService.register("duplicate-user", "secret123");

        assertThatThrownBy(() -> userRegistrationService.register("duplicate-user", "secret456"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Username already exists.");
    }

    @Test
    void registerRejectsBlankValues() {
        assertThatThrownBy(() -> userRegistrationService.register("   ", "secret123"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Username and password are required.");

        assertThatThrownBy(() -> userRegistrationService.register("valid-user", "   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Username and password are required.");
    }
}
