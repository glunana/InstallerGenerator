package com.example.installer.dto;

public class ProjectRequestDto {
    private String projectName;
    private String version;

    private String installPath;
    private boolean createDesktopShortcut;
    private String language;
    private String mainClass;
    private String requirements;

    public ProjectRequestDto() {
    }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getInstallPath() { return installPath; }
    public void setInstallPath(String installPath) { this.installPath = installPath; }

    public boolean isCreateDesktopShortcut() { return createDesktopShortcut; }
    public void setCreateDesktopShortcut(boolean createDesktopShortcut) { this.createDesktopShortcut = createDesktopShortcut; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getMainClass() { return mainClass; }
    public void setMainClass(String mainClass) { this.mainClass = mainClass; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }
}