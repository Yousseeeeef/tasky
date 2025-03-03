package com.example.tasky.service;

import com.example.tasky.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {
    private TaskService taskService;
    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }
    @Test
    void testGetAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        assertNotNull(tasks); // Vérifie que la liste n'est pas nulle
        assertEquals(3, tasks.size()); // Vérifie qu'il y a 3 tâches initialement
    }

    @Test
    void testAddTask() {
        // Ajouter une tâche et vérifier qu'elle a bien été ajoutée
        Task newTask = new Task(4L, "Faire du sport", "Aller courir", LocalDate.now(), "Open", null, null);
        taskService.addTask(newTask);

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(4, tasks.size()); // Vérifie qu'il y a maintenant 4 tâches
        assertTrue(tasks.contains(newTask)); // Vérifie que la tâche est bien ajoutée
    }

    @Test
    void testRemoveTask() {
        // Supprimer une tâche et vérifier qu'elle a bien été supprimée
        Task taskToRemove = taskService.getAllTasks().get(0); // Supposons qu'on supprime la première tâche
        taskService.removeTask(taskToRemove.getId());

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(2, tasks.size()); // Vérifie qu'il y a maintenant 2 tâches
        assertFalse(tasks.contains(taskToRemove)); // Vérifie que la tâche est bien supprimée
    }

    @Test
    void testUpdateTask() {
        // Modifier une tâche existante et vérifier la mise à jour
        Task existingTask = taskService.getAllTasks().get(0);
        Task updatedTask = new Task(existingTask.getId(), "Nouveau titre", "Nouvelle description", LocalDate.now(), "Closed", null, null);

        taskService.updateTask(existingTask.getId(), updatedTask);

        List<Task> tasks = taskService.getAllTasks();
        assertTrue(tasks.contains(updatedTask)); // Vérifie que la tâche a été mise à jour
    }
    }

