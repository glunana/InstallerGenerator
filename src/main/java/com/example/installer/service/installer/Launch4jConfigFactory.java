package com.example.installer.service.installer;

import com.example.installer.File;
import com.example.installer.Project;
import org.springframework.stereotype.Service;

@Service
public class Launch4jConfigFactory implements InstallerFactory {

    private final Launch4jConfigBuilder configBuilder;

    public Launch4jConfigFactory(Launch4jConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
    }

    @Override
    public InstallerType supportsType() {
        return InstallerType.CONFIG_ONLY;
    }

    @Override
    public String generateInstaller(File jarFile, Project project) throws Exception {
        String mainClass = project.getInstallerSettings().getMainClass();
        return configBuilder.setJarPath(jarFile.getPath())
                .setExePath(jarFile.getPath().replace(".jar", ".exe"))
                .setMainClass(mainClass)
                .setMinJreVersion("16")
                .setCreateDesktopShortcut(project.getInstallerSettings().isCreateDesktopShortcut())
                .build();
    }
}
