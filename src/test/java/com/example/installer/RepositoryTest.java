package com.example.installer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.installer.repository.ProjectRepository;
import com.example.installer.repository.FileRepository;

@SpringBootTest(classes = Application.class)
public class RepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void testProjectRepository() {
        // Тестування операцій з проектами
        Project project = new Project("Test Project", "1.0");
        project.setInstallerSettings("C:/Program Files/InstallerGenerator", true, "English");
        projectRepository.save(project);
        System.out.println("Saved project: " + project.getProjectName());

        // Перевірка, чи проект був збережений
        Project savedProject = projectRepository.findById(project.getId()).orElse(null);
        assert savedProject != null;
        assert "Test Project".equals(savedProject.getProjectName());
    }

    @Test
    public void testFileRepository() {
        // 1. Створюємо та зберігаємо Project перед використанням
        Project projectForFile = new Project("Linked Project", "2.0");
        projectForFile.setInstallerSettings("C:/Program Files/InstallerGenerator", true, "English");
        projectRepository.save(projectForFile); // Збереження Project

        // 2. Створюємо File, використовуючи збережений Project
        File file = new File("TestFile", "exe", "/path/to/file", projectForFile);

        // 3. Зберігаємо File
        fileRepository.save(file);
        System.out.println("Saved file: " + file.getFileName());

        // Перевірка, чи файл був збережений
        File savedFile = fileRepository.findById(file.getId()).orElse(null);
        assert savedFile != null;
        assert "TestFile".equals(savedFile.getFileName());
    }
}
