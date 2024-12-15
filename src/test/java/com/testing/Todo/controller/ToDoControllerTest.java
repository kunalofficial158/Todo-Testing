package com.testing.Todo.controller;

import com.testing.Todo.model.ToDo;
import com.testing.Todo.service.ToDoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToDoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ToDoService toDoService;

    @Test
    public void testCreateToDo() {
        ToDo toDo = new ToDo();
        toDo.setTitle("Test Title");
        toDo.setDescription("Test Description");
        toDo.setCompleted(false);

        ResponseEntity<ToDo> response = restTemplate.postForEntity("/todos", toDo, ToDo.class);

        assertNotNull(response.getBody().getId());
        assertEquals("Test Title", response.getBody().getTitle());
        assertEquals("Test Description", response.getBody().getDescription());
        assertFalse(response.getBody().isCompleted());
    }

    @Test
    public void testGetToDoById() {
        // First, create a new ToDo
        ToDo toDo = new ToDo();
        toDo.setTitle("Test Get Title");
        toDo.setDescription("Test Get Description");
        toDo.setCompleted(false);

        ResponseEntity<ToDo> createResponse = restTemplate.postForEntity("/todos", toDo, ToDo.class);
        Long toDoId = createResponse.getBody().getId();

        // Get the ToDo by ID
        ResponseEntity<ToDo> getResponse = restTemplate.getForEntity("/todos/" + toDoId, ToDo.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Test Get Title", getResponse.getBody().getTitle());
        assertEquals("Test Get Description", getResponse.getBody().getDescription());
        assertFalse(getResponse.getBody().isCompleted());
    }

    @Test
    public void testUpdateToDo() {
        // First, create a new ToDo
        ToDo toDo = new ToDo();
        toDo.setTitle("Test Update Title");
        toDo.setDescription("Test Update Description");
        toDo.setCompleted(false);

        ResponseEntity<ToDo> createResponse = restTemplate.postForEntity("/todos", toDo, ToDo.class);
        Long toDoId = createResponse.getBody().getId();

        // Update the ToDo
        toDo.setTitle("Updated Title");
        toDo.setDescription("Updated Description");
        toDo.setCompleted(true);

        HttpEntity<ToDo> requestUpdate = new HttpEntity<>(toDo);
        ResponseEntity<ToDo> updateResponse = restTemplate.exchange("/todos/" + toDoId, HttpMethod.PUT, requestUpdate, ToDo.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("Updated Title", updateResponse.getBody().getTitle());
        assertEquals("Updated Description", updateResponse.getBody().getDescription());
        assertTrue(updateResponse.getBody().isCompleted());
    }

    @Test
    public void testDeleteToDo() {
        // First, create a new ToDo
        ToDo toDo = new ToDo();
        toDo.setTitle("Test Delete Title");
        toDo.setDescription("Test Delete Description");
        toDo.setCompleted(false);

        ResponseEntity<ToDo> createResponse = restTemplate.postForEntity("/todos", toDo, ToDo.class);
        Long toDoId = createResponse.getBody().getId();

        restTemplate.delete("/todos/" + toDoId);

        ResponseEntity<ToDo> getResponse = restTemplate.getForEntity("/todos/" + toDoId, ToDo.class);

        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
