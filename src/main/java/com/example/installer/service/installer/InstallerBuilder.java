package com.example.installer.service.installer;

import com.example.installer.File;
import com.example.installer.Project;
import com.example.installer.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstallerBuilder {

    private final JarValidator jarValidator;
    private final ExeGenerator exeGenerator;
    private final ProjectRepository projectRepository;

    public InstallerBuilder(JarValidator jarValidator,
                            ExeGenerator exeGenerator,
                            ProjectRepository projectRepository) {
        this.jarValidator = jarValidator;
        this.exeGenerator = exeGenerator;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public String buildInstallerForProject(Long projectId) throws Exception {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        ProjectFileIterator it = new ProjectFileIterator(project.getFiles());

        List<File> jarCandidates = new ArrayList<>();
        while (it.hasNext()) {
            File f = it.next();
            if (jarValidator.isValidJar(f)) {
                jarCandidates.add(f);
            }
        }

        if (jarCandidates.isEmpty()) {
            throw new IllegalStateException("No .jar file found in project.");
        }
        if (jarCandidates.size() > 1) {
            throw new IllegalStateException("More than one .jar file found. Only one is allowed.");
        }

        File jarFile = jarCandidates.get(0);

        String exePath = exeGenerator.generateExe(jarFile, project);

        return exePath;
    }
}
