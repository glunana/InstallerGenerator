package com.example.installer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private String version;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<File> files = new ArrayList<>();

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private InstallerSettings installerSettings;

    public Project() {
    }

    public Project(String projectName, String version) {
        this.projectName = projectName;
        this.version = version;
        this.installerSettings = new InstallerSettings("C:/Program Files/InstallerGenerator", false, "English");
        this.installerSettings.setProject(this);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public InstallerSettings getInstallerSettings() {
        return installerSettings;
    }

    public void setInstallerSettings(InstallerSettings installerSettings) {
        this.installerSettings = installerSettings;
    }

    // Метод для додавання файлів до проекту
    public void addFile(File file) {
        this.files.add(file);
        file.setProject(this);
    }

    // Метод для встановлення налаштувань інсталяції
    public void setInstallerSettings(String installPath, boolean createDesktopShortcut, String language) {
        if (this.installerSettings == null) {
            this.installerSettings = new InstallerSettings();
        }
        this.installerSettings.setInstallPath(installPath);
        this.installerSettings.setCreateDesktopShortcut(createDesktopShortcut);
        this.installerSettings.setLanguage(language);

        // Встановлюємо проект для installerSettings
        this.installerSettings.setProject(this);
    }


}
