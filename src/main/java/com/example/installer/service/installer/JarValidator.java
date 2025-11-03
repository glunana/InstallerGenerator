package com.example.installer.service.installer;

import com.example.installer.File;
import org.springframework.stereotype.Component;

@Component
public class JarValidator {

    public boolean isValidJar(File fileEntity) {
        if (fileEntity == null) {
            return false;
        }

        String name = fileEntity.getFileName();
        if (name == null) {
            return false;
        }

        return name.toLowerCase().endsWith(".jar");
    }
}
