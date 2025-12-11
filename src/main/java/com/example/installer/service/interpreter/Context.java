package com.example.installer.service.interpreter;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private final Map<String, String> data = new HashMap<>();

    public void set(String key, String value) {
        data.put(key.toLowerCase(), value.toLowerCase());
    }

    public String get(String key) {
        return data.get(key.toLowerCase());
    }

    public static Context getSystemContext() {
        Context ctx = new Context();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) ctx.set("os", "windows");
        else if (os.contains("mac")) ctx.set("os", "macos");
        else ctx.set("os", "linux");

        ctx.set("java", System.getProperty("java.version").split("\\.")[0]);
        return ctx;
    }
}