package com.example.installer.service.installer.bridge;

import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class Launch4jBuildEngine implements BuildEngine {

    private static final String LAUNCH4J_EXE = "C:\\Program Files (x86)\\Launch4j\\launch4j.exe";

    private static final String CONFIG_FILE_PATH = "D:\\Робочий стіл\\trpz\\InstallerGenerator\\generated\\launch4j-config.xml";

    @Override
    public void build(String configContent, Path outputExePath) throws Exception {
        if (!Files.exists(Paths.get(LAUNCH4J_EXE))) {
            throw new RuntimeException("Launch4j not found at " + LAUNCH4J_EXE);
        }

        Path configPath = Paths.get(CONFIG_FILE_PATH);

        if (configPath.getParent() != null) {
            Files.createDirectories(configPath.getParent());
        }

        try (FileWriter writer = new FileWriter(configPath.toFile())) {
            writer.write(configContent);
        }

        System.out.println("Config written to: " + configPath.toAbsolutePath());

        ProcessBuilder pb = new ProcessBuilder(
                LAUNCH4J_EXE,
                configPath.toAbsolutePath().toString()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Launch4j]: " + line);
            }
        }

        int exitCode = process.waitFor();


        if (exitCode != 0) {
            throw new RuntimeException("Launch4j failed with code " + exitCode);
        }

        System.out.println("Build process finished. Checking result at: " + outputExePath);
    }
}