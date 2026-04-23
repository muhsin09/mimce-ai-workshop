package com.mimce.workshop.service;

import com.mimce.workshop.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TodoServiceTest {

    private TodoService service;

    @BeforeEach
    void setUp() {
        service = new TodoService();
    }

    // --- getTodos ---

    @Test
    void returnsEmptyListForNewUser() {
        List<Todo> todos = service.getTodos("alice");

        assertThat(todos).isEmpty();
    }

    // --- addTodo ---

    @Test
    void addedTodoAppearsInUserList() {
        service.addTodo("alice", "Buy milk");

        List<Todo> todos = service.getTodos("alice");
        assertThat(todos).hasSize(1);
        assertThat(todos.get(0).getTitle()).isEqualTo("Buy milk");
    }

    @Test
    void newlyAddedTodoIsNotCompleted() {
        service.addTodo("alice", "Buy milk");

        Todo todo = service.getTodos("alice").get(0);
        assertThat(todo.isCompleted()).isFalse();
    }

    @Test
    void newlyAddedTodoHasAnId() {
        service.addTodo("alice", "Buy milk");

        Todo todo = service.getTodos("alice").get(0);
        assertThat(todo.getId()).isNotBlank();
    }

    @Test
    void blankTitleIsIgnoredAndNotAdded() {
        service.addTodo("alice", "   ");

        assertThat(service.getTodos("alice")).isEmpty();
    }

    @Test
    void titleIsTrimmedBeforeSaving() {
        service.addTodo("alice", "  Buy milk  ");

        assertThat(service.getTodos("alice").get(0).getTitle()).isEqualTo("Buy milk");
    }

    @Test
    void todosAreIsolatedBetweenUsers() {
        service.addTodo("alice", "Alice task");
        service.addTodo("bob", "Bob task");

        assertThat(service.getTodos("alice")).hasSize(1);
        assertThat(service.getTodos("alice").get(0).getTitle()).isEqualTo("Alice task");

        assertThat(service.getTodos("bob")).hasSize(1);
        assertThat(service.getTodos("bob").get(0).getTitle()).isEqualTo("Bob task");
    }

    // --- toggleTodo ---

    @Test
    void toggleSetsCompletedToTrue() {
        service.addTodo("alice", "Buy milk");
        String id = service.getTodos("alice").get(0).getId();

        service.toggleTodo("alice", id);

        assertThat(service.getTodos("alice").get(0).isCompleted()).isTrue();
    }

    @Test
    void toggleTwiceRestoresNotCompleted() {
        service.addTodo("alice", "Buy milk");
        String id = service.getTodos("alice").get(0).getId();

        service.toggleTodo("alice", id);
        service.toggleTodo("alice", id);

        assertThat(service.getTodos("alice").get(0).isCompleted()).isFalse();
    }

    @Test
    void toggleOnUnknownIdDoesNotThrow() {
        // should be a no-op, not an exception
        service.toggleTodo("alice", "nonexistent-id");

        assertThat(service.getTodos("alice")).isEmpty();
    }

    @Test
    void toggleDoesNotAffectOtherUsersItems() {
        service.addTodo("alice", "Alice task");
        service.addTodo("bob", "Bob task");
        String aliceId = service.getTodos("alice").get(0).getId();

        service.toggleTodo("alice", aliceId);

        assertThat(service.getTodos("bob").get(0).isCompleted()).isFalse();
    }

    // --- deleteTodo ---

    @Test
    void deletedTodoDisappearsFromList() {
        service.addTodo("alice", "Buy milk");
        String id = service.getTodos("alice").get(0).getId();

        service.deleteTodo("alice", id);

        assertThat(service.getTodos("alice")).isEmpty();
    }

    @Test
    void deleteOnUnknownIdDoesNotThrow() {
        service.deleteTodo("alice", "nonexistent-id");

        assertThat(service.getTodos("alice")).isEmpty();
    }

    @Test
    void deleteDoesNotAffectOtherUsersItems() {
        service.addTodo("alice", "Alice task");
        service.addTodo("bob", "Bob task");
        String aliceId = service.getTodos("alice").get(0).getId();

        service.deleteTodo("alice", aliceId);

        assertThat(service.getTodos("bob")).hasSize(1);
    }
}
