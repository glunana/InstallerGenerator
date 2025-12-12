package com.example.installer.client;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SimpleClient {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/api/create-project");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            String jsonInputString = """
                {
                    "projectName": "DistributedApp",
                    "version": "1.0.0",
                    "installPath": "C:/Temp/Dist",
                    "createDesktopShortcut": true,
                    "language": "English",
                    "mainClass": "com.test.Main",
                    "requirements": "os windows AND java 17"
                }
                """;

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == 200) {
                System.out.println("Success! Project created via REST API.");
            } else {
                System.out.println("Something went wrong.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}