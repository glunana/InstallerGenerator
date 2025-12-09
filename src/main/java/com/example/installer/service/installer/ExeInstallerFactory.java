package com.example.installer.service.installer;

import com.example.installer.File;
import com.example.installer.Project;
import com.example.installer.service.installer.bridge.GuiInstaller;
import org.springframework.stereotype.Service;

@Service
public class ExeInstallerFactory implements InstallerFactory {

    private final GuiInstaller guiInstaller;

    public ExeInstallerFactory(GuiInstaller guiInstaller) {
        this.guiInstaller = guiInstaller;
    }

    @Override
    public InstallerType supportsType() {
        return InstallerType.EXE;
    }

    @Override
    public String generateInstaller(File jarFile, Project project) throws Exception {

        return guiInstaller.generate(jarFile, project);
    }
}