package com.mimce.workshop.controller;

import com.mimce.workshop.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class TodoControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TodoService todoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @WithMockUser(username = "mimce")
    void postTodoAddsTodoAndRedirectsToDashboard() throws Exception {
        mockMvc.perform(post("/todos")
                .param("title", "Controller test task")
                .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/dashboard"));

        assertThat(todoService.getTodos("mimce"))
            .anyMatch(t -> t.getTitle().equals("Controller test task"));
    }

    @Test
    @WithMockUser(username = "mimce")
    void postToggleFlipsCompletedAndRedirects() throws Exception {
        todoService.addTodo("mimce", "Toggle me");
        String id = todoService.getTodos("mimce").stream()
            .filter(t -> t.getTitle().equals("Toggle me"))
            .findFirst().orElseThrow().getId();

        mockMvc.perform(post("/todos/{id}/toggle", id).with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/dashboard"));

        assertThat(todoService.getTodos("mimce"))
            .anyMatch(t -> t.getId().equals(id) && t.isCompleted());
    }

    @Test
    @WithMockUser(username = "mimce")
    void postDeleteRemovesTodoAndRedirects() throws Exception {
        todoService.addTodo("mimce", "Delete me");
        String id = todoService.getTodos("mimce").stream()
            .filter(t -> t.getTitle().equals("Delete me"))
            .findFirst().orElseThrow().getId();

        mockMvc.perform(post("/todos/{id}/delete", id).with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/dashboard"));

        assertThat(todoService.getTodos("mimce"))
            .noneMatch(t -> t.getId().equals(id));
    }

    @Test
    void unauthenticatedUserIsRedirectedToLogin() throws Exception {
        mockMvc.perform(post("/todos")
                .param("title", "Should fail")
                .with(csrf()))
            .andExpect(status().is3xxRedirection());
    }
}
