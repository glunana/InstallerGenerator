package com.example.installer;

import javax.persistence.*;

@Entity
@Table(name = "installer_settings")
public class InstallerSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String installPath;
    private boolean createDesktopShortcut;
    private String language;
    private String mainClass;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public InstallerSettings() {
    }

    public InstallerSettings(String installPath, boolean createDesktopShortcut, String language) {
        this.installPath = installPath;
        this.createDesktopShortcut = createDesktopShortcut;
        this.language = language;
    }

    private String requirements;

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    public boolean isCreateDesktopShortcut() {
        return createDesktopShortcut;
    }

    public void setCreateDesktopShortcut(boolean createDesktopShortcut) {
        this.createDesktopShortcut = createDesktopShortcut;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }
}
