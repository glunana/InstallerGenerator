package com.example.installer.service.installer;

import com.example.installer.File;
import com.example.installer.Project;

public interface InstallerFactory {
    InstallerType supportsType();

    String generateInstaller(File jarFile, Project project) throws Exception;
}
