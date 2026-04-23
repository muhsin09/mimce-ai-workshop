package com.mimce.workshop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class RegisterControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    void getRegisterReturnsRegisterView() throws Exception {
        mockMvc.perform(get("/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"));
    }

    @Test
    void postRegisterCreatesUserAndRedirectsToLogin() throws Exception {
        String username = "workshop-user-" + System.nanoTime();

        mockMvc.perform(post("/register")
                .param("username", username)
                .param("password", "secret123")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"))
            .andExpect(flash().attributeExists("successMessage"));
    }

    @Test
    void postRegisterDuplicateUserRedirectsBackWithError() throws Exception {
        String username = "duplicate-user-" + System.nanoTime();

        mockMvc.perform(post("/register")
                .param("username", username)
                .param("password", "secret123")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/login"));

        mockMvc.perform(post("/register")
                .param("username", username)
                .param("password", "secret123")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/register"))
            .andExpect(flash().attribute("errorMessage", "Bu kullanici adi zaten kullaniliyor."));
    }
}
