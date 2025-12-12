package com.example.installer.controller;

import com.example.installer.InstallerSettings;
import com.example.installer.Project;
import com.example.installer.dto.ProjectRequestDto;
import com.example.installer.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InstallerApiController {

    private final ProjectRepository projectRepository;

    public InstallerApiController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PostMapping("/create-project")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDto request) {
        try {
            Project project = new Project();
            project.setProjectName(request.getProjectName());
            project.setVersion(request.getVersion());

            InstallerSettings settings = new InstallerSettings();
            settings.setInstallPath(request.getInstallPath());
            settings.setCreateDesktopShortcut(request.isCreateDesktopShortcut());
            settings.setLanguage(request.getLanguage());
            settings.setMainClass(request.getMainClass());
            settings.setRequirements(request.getRequirements());

            settings.setProject(project);
            project.setInstallerSettings(settings);

            Project savedProject = projectRepository.save(project);

            return ResponseEntity.ok().body("Project created successfully with ID: " + savedProject.getId());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating project: " + e.getMessage());
        }
    }
}