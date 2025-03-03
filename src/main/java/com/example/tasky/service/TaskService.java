package com.example.tasky.service;

import com.example.tasky.dto.TaskDTO;
import com.example.tasky.mapper.TaskMapper;
import com.example.tasky.model.Task;
import com.example.tasky.model.User;
import com.example.tasky.model.Project;
import com.example.tasky.repository.TaskRepository;
import com.example.tasky.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService; // Inject UserService
    private final ProjectService projectService; // Inject ProjectService

    public TaskService(TaskRepository taskRepository, UserService userService, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        return TaskMapper.toDTO(task);
    }

    @Transactional
    public TaskDTO addTask(TaskDTO taskDTO) {
        User assignedTo = null;
        Project project = null;

        if (taskDTO.getAssignedToId() != null) {
            assignedTo = userService.getUserEntity(taskDTO.getAssignedToId());
        }
        if (taskDTO.getProjectId() != null) {
            project = projectService.getProjectEntity(taskDTO.getProjectId());
        }

        Task task = TaskMapper.toEntity(taskDTO, assignedTo, project);
        Task savedTask = taskRepository.save(task);
        return TaskMapper.toDTO(savedTask);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO updatedTaskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        User assignedTo = null;
        Project project = null;

        if (updatedTaskDTO.getAssignedToId() != null) {
            assignedTo = userService.getUserEntity(updatedTaskDTO.getAssignedToId());
        } else {
            assignedTo = existingTask.getAssignedTo();  // Keep existing if not provided
        }
        if (updatedTaskDTO.getProjectId() != null) {
            project = projectService.getProjectEntity(updatedTaskDTO.getProjectId());
        } else {
            project = existingTask.getProject(); // Keep existing if not provided
        }


        Task updatedTask = TaskMapper.toEntity(updatedTaskDTO, assignedTo, project);
        updatedTask.setId(existingTask.getId()); // Ensure ID is not overwritten
        updatedTask = taskRepository.save(updatedTask);

        return TaskMapper.toDTO(updatedTask);
    }


    @Transactional
    public void removeTask(Long id) {
        taskRepository.deleteById(id);
    }
}