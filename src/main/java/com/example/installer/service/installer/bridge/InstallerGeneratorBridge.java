package com.example.installer.service.installer.bridge;

import com.example.installer.File;
import com.example.installer.Project;
import com.example.installer.service.installer.Launch4jConfigBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class InstallerGeneratorBridge {

    protected final BuildEngine buildEngine;

    protected final Launch4jConfigBuilder configBuilder;

    protected InstallerGeneratorBridge(BuildEngine buildEngine, Launch4jConfigBuilder configBuilder) {
        this.buildEngine = buildEngine;
        this.configBuilder = configBuilder;
    }

    public String generate(File jarFile, Project project) throws Exception {
        String installDir = project.getInstallerSettings().getInstallPath();
        if (installDir == null || installDir.trim().isEmpty()) {
            throw new IllegalArgumentException("Install Path is not set! Please configure it in project settings.");
        }

        String projectName = project.getProjectName();
        Path exePath = Paths.get(installDir, projectName + ".exe");
        String mainClass = project.getInstallerSettings().getMainClass();

        configBuilder.setJarPath(jarFile.getPath())
                .setExePath(exePath.toString())
                .setMainClass(mainClass)
                .setMinJreVersion("16")
                .setCreateDesktopShortcut(project.getInstallerSettings().isCreateDesktopShortcut());

        configureSpecifics();

        String configContent = configBuilder.build();

        buildEngine.build(configContent, exePath);

        return exePath.toAbsolutePath().toString();
    }

    protected abstract void configureSpecifics();
}