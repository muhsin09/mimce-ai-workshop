package com.mimce.workshop.controller;

import com.mimce.workshop.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Renders the dashboard view showing the user's todo list.
 */
@Controller
public class DashboardController {

    private final TodoService todoService;

    public DashboardController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        var todos = todoService.getTodos(principal.getName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("todos", todos);
        model.addAttribute("completedCount", todos.stream().filter(t -> t.isCompleted()).count());
        return "dashboard";
    }
}
