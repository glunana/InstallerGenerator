package com.example.installer.service.interpreter;

public class JavaVersionExpression implements Expression {
    private final int minVersion;

    public JavaVersionExpression(String version) {
        this.minVersion = Integer.parseInt(version);
    }

    @Override
    public boolean interpret(Context context) {
        String currentJavaStr = context.get("java");
        if (currentJavaStr == null) return false;

        try {
            int currentJava = Integer.parseInt(currentJavaStr);
            return currentJava >= minVersion;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}