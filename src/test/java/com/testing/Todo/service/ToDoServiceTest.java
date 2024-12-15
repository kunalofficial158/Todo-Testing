package com.testing.Todo.service;

import com.testing.Todo.model.ToDo;
import com.testing.Todo.repository.ToDoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    public void testCreateToDo() {
        ToDo toDo = new ToDo();
        toDo.setTitle("Test Title");
        toDo.setDescription("Test Description");
        toDo.setCompleted(false);

        ToDo savedToDo = toDoService.createToDo(toDo);

        assertNotNull(savedToDo.getId());
        assertEquals("Test Title", savedToDo.getTitle());
        assertEquals("Test Description", savedToDo.getDescription());
        assertFalse(savedToDo.isCompleted());
    }

    @Test
    public void testGetToDoById() {
        ToDo toDo = new ToDo();
        toDo.setTitle("Test Title");
        toDo.setDescription("Test Description");
        toDo.setCompleted(false);

        ToDo savedToDo = toDoRepository.save(toDo);

        Optional<ToDo> foundToDo = toDoService.getToDoById(savedToDo.getId());

        assertTrue(foundToDo.isPresent());
        assertEquals(savedToDo.getId(), foundToDo.get().getId());
    }

    @Test
    public void testUpdateToDo() {
        // First, create a new ToDo
        ToDo toDo = new ToDo();
        toDo.setTitle("Test Update Title");
        toDo.setDescription("Test Update Description");
        toDo.setCompleted(false);

        ToDo savedToDo = toDoRepository.save(toDo);

        // Update the ToDo
        savedToDo.setTitle("Updated Title");
        savedToDo.setDescription("Updated Description");
        savedToDo.setCompleted(true);

        ToDo updatedToDo = toDoService.updateToDo(savedToDo.getId(), savedToDo);

        assertNotNull(updatedToDo);
        assertEquals("Updated Title", updatedToDo.getTitle());
        assertEquals("Updated Description", updatedToDo.getDescription());
        assertTrue(updatedToDo.isCompleted());
    }

    @Test
    public void testDeleteToDo() {

        ToDo toDo = new ToDo();
        toDo.setTitle("Test Delete Title");
        toDo.setDescription("Test Delete Description");
        toDo.setCompleted(false);

        ToDo savedToDo = toDoRepository.save(toDo);

        toDoService.deleteToDo(savedToDo.getId());

        Optional<ToDo> foundToDo = toDoRepository.findById(savedToDo.getId());
        assertFalse(foundToDo.isPresent());
    }
}
