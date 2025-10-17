package com.example.installer.service;

import com.example.installer.Project;
import com.example.installer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Логіка для створення нового проекту
    public Project createNewProject(String projectName, String version) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setVersion(version);

        return projectRepository.save(project);
    }
}
