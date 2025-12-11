package com.example.installer.service.installer;

import com.example.installer.File;
import com.example.installer.Project;
import com.example.installer.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.installer.service.interpreter.Context;
import com.example.installer.service.interpreter.Expression;
import com.example.installer.service.interpreter.RequirementsParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InstallerBuilder {

    private final JarValidator jarValidator;
    private final ProjectRepository projectRepository;

    private final Map<InstallerType, InstallerFactory> factories;

    public InstallerBuilder(JarValidator jarValidator,
                            ProjectRepository projectRepository,
                            List<InstallerFactory> factoryList) {
        this.jarValidator = jarValidator;
        this.projectRepository = projectRepository;

        this.factories = factoryList.stream()
                .collect(Collectors.toMap(InstallerFactory::supportsType, f -> f));
    }

    @Transactional
    public String buildInstallerForProject(Long projectId) throws Exception {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        String reqs = project.getInstallerSettings().getRequirements();

        if (reqs != null && !reqs.trim().isEmpty()) {
            System.out.println("Interpreter: Checking system requirements -> " + reqs);

            Expression expressionTree = RequirementsParser.parse(reqs);

            Context systemContext = Context.getSystemContext();

            boolean isCompatible = expressionTree.interpret(systemContext);

            if (!isCompatible) {
                throw new RuntimeException("System requirements check failed! " +
                        "Required: [" + reqs + "], but system is incompatible.");
            }
            System.out.println("Interpreter: System check passed.");
        }

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

        InstallerType targetType = InstallerType.EXE;

        InstallerFactory factory = factories.get(targetType);

        if (factory == null) {
            throw new UnsupportedOperationException("Type not supported: " + targetType);
        }

        return factory.generateInstaller(jarFile, project);
    }
}

