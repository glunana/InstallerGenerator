package com.example.installer.service.installer;

import com.example.installer.File;
import com.example.installer.Project;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ExeGenerator {

    private static final String LAUNCH4J_PATH = "C:\\Program Files (x86)\\Launch4j\\launch4j.exe";

    public String generateExe(File jarFile, Project project) throws Exception {
        if (!Files.exists(Paths.get(LAUNCH4J_PATH))) {
            throw new IOException("Launch4j not found at " + LAUNCH4J_PATH);
        }

        String configFile = createLaunch4jConfig(jarFile, project);

        ProcessBuilder pb = new ProcessBuilder(
                LAUNCH4J_PATH,
                configFile
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Launch4j failed with code " + exitCode);
        }

        Path exePath = Paths.get("src/main/resources/static/uploads/" +
                jarFile.getFileName().replace(".jar", ".exe"));

        return exePath.toAbsolutePath().toString();
    }

    private String createLaunch4jConfig(File jarFile, Project project) throws IOException {
        String installDir = "D:\\";
        String mainClass = "com.example.testjarproject.HelloApplication";
        String minJreVersion = "16";

        if (project.getInstallerSettings() != null && project.getInstallerSettings().getInstallPath() != null) {
            installDir = project.getInstallerSettings().getInstallPath();
        }

        Path exePath = Paths.get(installDir, jarFile.getFileName().replace(".jar", ".exe"));

        String config = """
            <launch4jConfig>
                <dontWrapJar>false</dontWrapJar>
                <headerType>gui</headerType>
                <jar>%s</jar>
                <outfile>%s</outfile>
                <errTitle>Installer Generator</errTitle>
                <classPath>
                    <mainClass>%s</mainClass>
                </classPath>
                <jre>
                    <minVersion>%s</minVersion>
                </jre>
            </launch4jConfig>
            """.formatted(jarFile.getPath(), exePath.toString(), mainClass, minJreVersion);

        Path tempConfig = Paths.get("src/main/resources/static/uploads/launch4j-" + UUID.randomUUID() + ".xml");
        try (FileWriter writer = new FileWriter(tempConfig.toFile())) {
            writer.write(config);
        }

        return tempConfig.toAbsolutePath().toString();
    }

}
