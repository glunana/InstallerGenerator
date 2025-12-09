package com.example.installer.service.installer.bridge;

import com.example.installer.service.installer.Launch4jConfigBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInstaller extends InstallerGeneratorBridge {

    public ConsoleInstaller(BuildEngine buildEngine, Launch4jConfigBuilder configBuilder) {
        super(buildEngine, configBuilder);
    }

    @Override
    protected void configureSpecifics() {
        configBuilder.setHeaderType("console");
    }
}