package com.example.installer.controller;

import com.example.installer.Project;
import com.example.installer.File;
import com.example.installer.InstallerSettings;
import com.example.installer.repository.ProjectRepository;
import com.example.installer.service.installer.InstallerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.Set;

@Controller
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final InstallerBuilder installerBuilder;

    @Autowired
    public ProjectController(ProjectRepository projectRepository,
                             InstallerBuilder installerBuilder) {
        this.projectRepository = projectRepository;
        this.installerBuilder = installerBuilder;
    }

    @GetMapping("/createProject")
    public String showCreateProjectForm() {
        return "createProject";
    }

    @PostMapping("/createProject")
    public String submitProject(@RequestParam String projectName,
                                @RequestParam String version,
                                RedirectAttributes redirectAttributes) {
        Project p = new Project();
        p.setProjectName(projectName);
        p.setVersion(version);
        projectRepository.save(p);

        redirectAttributes.addFlashAttribute("message",
                "Проєкт '" + projectName + "' успішно створено!");
        return "redirect:/createProject";
    }

    @GetMapping("/projects")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "projects";
    }

    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model,
                                 RedirectAttributes ra) {
        Optional<Project> opt = projectRepository.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("message", "Проєкт з id=" + id + " не знайдено.");
            return "redirect:/projects";
        }

        Project project = opt.get();

        model.addAttribute("project", project);
        return "project-details";
    }


    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable Long id, RedirectAttributes ra) {
        projectRepository.deleteById(id);
        ra.addFlashAttribute("message", "Проєкт видалено.");
        return "redirect:/projects";
    }

    @PostMapping("/projects/{id}/setInstallerSettings")
    public String setInstallerSettings(@PathVariable Long id,
                                       @RequestParam String installPath,
                                       @RequestParam(required = false, defaultValue = "false") boolean createDesktopShortcut,
                                       @RequestParam String language,
                                       @RequestParam String mainClass, // Додаємо параметр mainClass
                                       RedirectAttributes redirectAttributes) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Project not found");
            return "redirect:/projects";
        }

        Project project = projectOpt.get();

        if (project.getInstallerSettings() == null) {
            InstallerSettings newInstallerSettings = new InstallerSettings(installPath, createDesktopShortcut, language);
            newInstallerSettings.setMainClass(mainClass);  // Зберігаємо введений mainClass
            newInstallerSettings.setProject(project);
            project.setInstallerSettings(newInstallerSettings);
        } else {
            project.setInstallerSettings(installPath, createDesktopShortcut, language);
            project.getInstallerSettings().setMainClass(mainClass); // Оновлюємо mainClass
        }

        projectRepository.save(project);
        redirectAttributes.addFlashAttribute("message", "Installer settings updated for project " + project.getProjectName());
        return "redirect:/projects/" + id;
    }


    @PostMapping("/projects/{id}/addFiles")
    public String addFilesToProject(@PathVariable Long id, @RequestParam("files") MultipartFile[] files,
                                    RedirectAttributes redirectAttributes) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Project not found");
            return "redirect:/projects";
        }

        Project project = projectOpt.get();
        for (MultipartFile file : files) {
            Set<String> allowedTypes = Set.of("zip", "tar", "jar");
            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);

            if (!allowedTypes.contains(fileExtension)) {
                redirectAttributes.addFlashAttribute("message", "Invalid file type: " + file.getOriginalFilename());
                return "redirect:/projects/" + id;
            }

            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";
            Path path = Paths.get(uploadDir + file.getOriginalFilename());

            try {
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("message", "Error saving file: " + e.getMessage());
                return "redirect:/projects/" + id;
            }

            File newFile = new File();
            newFile.setFileName(file.getOriginalFilename());
            newFile.setFileType(file.getContentType());
            newFile.setPath(path.toString());
            newFile.setProject(project);
            project.addFile(newFile);
        }

        projectRepository.save(project);

        redirectAttributes.addFlashAttribute("message", "Files added successfully to project " + project.getProjectName());
        return "redirect:/projects/" + id;
    }

    @PostMapping("/projects/{id}/generateInstaller")
    public String generateInstaller(@PathVariable Long id,
                                    RedirectAttributes redirectAttributes) {
        try {
            String exePath = installerBuilder.buildInstallerForProject(id);
            redirectAttributes.addFlashAttribute(
                    "message",
                    "Installer generated successfully: " + exePath
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    "Failed to generate installer: " + e.getMessage()
            );
        }
        return "redirect:/projects/" + id;
    }


}
