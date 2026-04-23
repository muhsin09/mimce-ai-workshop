package com.mimce.workshop.service;

import com.mimce.workshop.model.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TodoService {

    private final ConcurrentHashMap<String, List<Todo>> store = new ConcurrentHashMap<>();

    public List<Todo> getTodos(String username) {
        return Collections.unmodifiableList(
            store.getOrDefault(username, Collections.emptyList())
        );
    }

    public void addTodo(String username, String title) {
        if (title == null || title.isBlank()) return;
        store.computeIfAbsent(username, k -> new ArrayList<>())
             .add(new Todo(title.trim()));
    }

    public void deleteTodo(String username, String id) {
        List<Todo> todos = store.get(username);
        if (todos != null) {
            todos.removeIf(t -> t.getId().equals(id));
        }
    }

    public void toggleTodo(String username, String id) {
        List<Todo> todos = store.get(username);
        if (todos != null) {
            todos.stream()
                 .filter(t -> t.getId().equals(id))
                 .findFirst()
                 .ifPresent(t -> t.setCompleted(!t.isCompleted()));
        }
    }
}
