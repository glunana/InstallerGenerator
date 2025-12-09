package com.example.installer.service.installer.bridge;

import java.nio.file.Path;

public interface BuildEngine {
    void build(String configContent, Path outputExePath) throws Exception;
}