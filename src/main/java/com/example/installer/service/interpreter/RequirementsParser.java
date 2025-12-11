package com.example.installer.service.interpreter;

public class RequirementsParser {

    public static Expression parse(String requirements) {
        if (requirements == null || requirements.isEmpty()) {
            return context -> true;
        }

        String[] parts = requirements.split(" AND ");
        Expression finalExpression = null;

        for (String part : parts) {
            Expression currentExpr = parseSingleExpression(part.trim());

            if (finalExpression == null) {
                finalExpression = currentExpr;
            } else {
                finalExpression = new AndExpression(finalExpression, currentExpr);
            }
        }

        return finalExpression;
    }

    private static Expression parseSingleExpression(String token) {
        String[] segments = token.split(" ");
        if (segments.length != 2) {
            throw new IllegalArgumentException("Invalid expression format: " + token);
        }

        String key = segments[0];
        String value = segments[1];

        if (key.equalsIgnoreCase("os")) {
            return new OsExpression(value);
        } else if (key.equalsIgnoreCase("java")) {
            return new JavaVersionExpression(value);
        } else {
            throw new IllegalArgumentException("Unknown requirement key: " + key);
        }
    }
}