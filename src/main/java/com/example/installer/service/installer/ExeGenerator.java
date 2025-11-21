package com.example.installer.service.installer;

import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ExeGenerator {

    private static final String LAUNCH4J_EXE = "C:\\Program Files (x86)\\Launch4j\\launch4j.exe";

    public String generate(String xmlConfig, String exeOutputPath) throws Exception {

        Path tempConfig = Paths.get("generated/launch4j-config.xml");
        try (FileWriter writer = new FileWriter(tempConfig.toFile())) {
            writer.write(xmlConfig);
        }

        ProcessBuilder pb = new ProcessBuilder(
                LAUNCH4J_EXE,
                tempConfig.toAbsolutePath().toString()
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Launch4j error. Exit code = " + exitCode);
        }

        return exeOutputPath;
    }
}
