package com.mimce.workshop.controller;

import com.mimce.workshop.service.TodoService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/todos")
@Validated
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public String addTodo(Principal principal,
                          @RequestParam @NotBlank @Size(max = 255) String title) {
        todoService.addTodo(principal.getName(), title);
        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTodo(Principal principal, @PathVariable String id) {
        todoService.toggleTodo(principal.getName(), id);
        return "redirect:/dashboard";
    }

    @PostMapping("/{id}/delete")
    public String deleteTodo(Principal principal, @PathVariable String id) {
        todoService.deleteTodo(principal.getName(), id);
        return "redirect:/dashboard";
    }
}
