package com.example.installer;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "exe_files")
public class ExeFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // первинний ключ

    private String exeFileName; // Ім'я .exe файлу
    private boolean isCreated;  // Статус того, чи був файл створений

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;


    public ExeFile() {

    }

    public ExeFile(String exeFileName, Project project) {
        this.exeFileName = exeFileName;
        this.project = project;
        this.isCreated = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExeFileName() {
        return exeFileName;
    }

    public void setExeFileName(String exeFileName) {
        this.exeFileName = exeFileName;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    // Генерація .exe файлу
    public void generateExe() {
        System.out.println("Generating .exe file: " + exeFileName);
        this.isCreated = true;
    }

    // Перевірка валідності .exe файлу
    public boolean validateExe() {
        File file = new File(exeFileName);
        return file.exists() && file.isFile();
    }

    // Отримання детальної інформації про .exe файл
    public String getExeFileDetails() {
        return "ExeFile: " + exeFileName + ", Created: " + isCreated;
    }
}
