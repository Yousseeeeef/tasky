package com.example.tasky.mapper;

import com.example.tasky.dto.ProjectDTO;
import com.example.tasky.model.Project;

public class ProjectMapper {
    public static ProjectDTO toDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }

    public static Project toEntity(ProjectDTO dto) {
        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        return project;
    }
}
