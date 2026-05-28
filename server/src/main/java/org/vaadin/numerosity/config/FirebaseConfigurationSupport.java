package org.vaadin.numerosity.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Helper for determining whether Firebase is configured or the app should run in demo mode.
 */
public final class FirebaseConfigurationSupport {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private FirebaseConfigurationSupport() {
    }

    public static boolean isDemoMode(String credentialsPath, String projectId) {
        return !hasRealFirebaseConfiguration(credentialsPath, projectId);
    }

    static boolean isDemoModeFromJson(String credentialsJson, String projectId) {
        return !hasRealFirebaseConfigurationFromJson(credentialsJson, projectId);
    }

    public static boolean hasRealFirebaseConfiguration(String credentialsPath, String projectId) {
        if (credentialsPath == null || credentialsPath.isBlank() || projectId == null || projectId.isBlank()) {
            return false;
        }

        try (InputStream inputStream = openCredentialsStream(credentialsPath)) {
            String credentialsJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return hasRealFirebaseConfigurationFromJson(credentialsJson, projectId);
        } catch (IOException e) {
            return false;
        }
    }

    static boolean hasRealFirebaseConfigurationFromJson(String credentialsJson, String projectId) {
        if (credentialsJson == null || credentialsJson.isBlank() || projectId == null || projectId.isBlank()) {
            return false;
        }

        try {
            JsonNode root = OBJECT_MAPPER.readTree(credentialsJson);
            return hasConfiguredValue(root, "project_id")
                    && hasConfiguredValue(root, "private_key")
                    && hasConfiguredValue(root, "client_email")
                    && hasConfiguredValue(root, "private_key_id");
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean hasConfiguredValue(JsonNode root, String fieldName) {
        JsonNode field = root == null ? null : root.get(fieldName);
        return field != null
                && !field.isNull()
                && !field.asText("").isBlank()
                && !field.asText("").startsWith("YOUR_");
    }

    public static InputStream openCredentialsStream(String credentialsPath) throws IOException {
        Path filesystemPath = Paths.get(credentialsPath);
        if (Files.exists(filesystemPath)) {
            return Files.newInputStream(filesystemPath);
        }

        ClassPathResource classPathResource = new ClassPathResource(credentialsPath);
        if (classPathResource.exists()) {
            return classPathResource.getInputStream();
        }

        throw new IOException("Firebase credentials file not found: " + credentialsPath);
    }
}
