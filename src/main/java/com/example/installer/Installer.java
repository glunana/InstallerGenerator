package com.example.installer;

import java.io.IOException;

public class Installer {
    private ExeFile exeFile;
    private String installPath;

    public Installer(ExeFile exeFile, String installPath) {
        this.exeFile = exeFile;
        this.installPath = installPath;
    }

    public boolean isReadyToInstall() {
        return exeFile.isCreated() && exeFile.validateExe();
    }

    public void startInstallation() {
        if (isReadyToInstall()) {
            System.out.println("Starting installation...");
            try {
                ProcessBuilder builder = new ProcessBuilder(exeFile.getExeFileName());
                builder.directory(new java.io.File(installPath));
                builder.start();
            } catch (IOException e) {
                System.out.println("Error occurred while starting the installation: " + e.getMessage());
            }
        } else {
            System.out.println("The installer is not ready or the file is invalid.");
        }
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }
}
