package com.example.tasky.controller;

import com.example.tasky.dto.TaskDTO;
import com.example.tasky.service.TaskService;
import com.example.tasky.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Récupérer toutes les tâches
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id,long cin) {
        try {
            TaskDTO taskDTO = taskService.getTaskById(id);
            return new ResponseEntity<>(taskDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Ajouter une nouvelle tâche
    @PostMapping
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO createdTask = taskService.addTask(taskDTO);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // Supprimer une tâche par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.removeTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Modifier une tâche existante
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}