package com.example.installer.service.installer;

import com.example.installer.File;
import com.example.installer.Project;
import com.example.installer.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        List<File> jars = new ArrayList<>();

        while (it.hasNext()) {
            File f = it.next();
            if (jarValidator.isValidJar(f)) {
                jars.add(f);
            }
        }

        if (jars.isEmpty())
            throw new IllegalStateException("No JAR file found in project.");
        if (jars.size() > 1)
            throw new IllegalStateException("Only one JAR file allowed.");

        File jarFile = jars.get(0);

        String installDir = project.getInstallerSettings().getInstallPath();
        Path exePath = Paths.get(installDir, jarFile.getFileName().replace(".jar", ".exe"));

        String xml = new Launch4jConfigBuilder()
                .setJarPath(jarFile.getPath())
                .setExePath(exePath.toString())
                .setMainClass("com.example.testjarproject.HelloApplication")
                .setMinJreVersion("16")
                .setCreateDesktopShortcut(project.getInstallerSettings().isCreateDesktopShortcut())
                .build();

        return exeGenerator.generate(xml, exePath.toString());
    }
}
