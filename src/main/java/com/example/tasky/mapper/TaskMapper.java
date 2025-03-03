package com.example.tasky.mapper;

import com.example.tasky.dto.TaskDTO;
import com.example.tasky.model.Task;
import com.example.tasky.model.User;
import com.example.tasky.model.Project;

public class TaskMapper {
    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getAssignedTo() != null ? task.getAssignedTo().getId() : null,
                task.getProject() != null ? task.getProject().getId() : null
        );
    }

    public static Task toEntity(TaskDTO dto, User assignedTo, Project project) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());
        task.setAssignedTo(assignedTo);
        task.setProject(project);
        return task;
    }
}
