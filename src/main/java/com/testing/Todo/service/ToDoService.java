package com.testing.Todo.service;

import com.testing.Todo.model.ToDo;
import com.testing.Todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    public Optional<ToDo> getToDoById(Long id) {
        return toDoRepository.findById(id);
    }

    public ToDo createToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public ToDo updateToDo(Long id, ToDo toDo) {
        Optional<ToDo> existingToDo = toDoRepository.findById(id);
        if (existingToDo.isPresent()) {
            ToDo updatedToDo = existingToDo.get();
            updatedToDo.setTitle(toDo.getTitle());
            updatedToDo.setDescription(toDo.getDescription());
            updatedToDo.setCompleted(toDo.isCompleted());
            return toDoRepository.save(updatedToDo);
        } else {
            throw new RuntimeException("ToDo not found with id " + id);
        }
    }

    public void deleteToDo(Long id) {
        toDoRepository.deleteById(id);
    }
}
