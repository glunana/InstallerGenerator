package com.example.installer.service.interpreter;

public class OsExpression implements Expression {
    private final String requiredOs;

    public OsExpression(String requiredOs) {
        this.requiredOs = requiredOs.toLowerCase();
    }

    @Override
    public boolean interpret(Context context) {
        String currentOs = context.get("os");
        return currentOs != null && currentOs.equals(requiredOs);
    }
}