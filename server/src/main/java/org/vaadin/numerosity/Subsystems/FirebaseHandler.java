package org.vaadin.numerosity.Subsystems;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * Handler class for initializing Firebase with custom database URL and credentials.
 */
public class FirebaseHandler {
    private String databaseUrl;
    private String pathToKey;

    public FirebaseHandler(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void initialize() throws IOException {
        if (pathToKey == null || pathToKey.isEmpty()) {
            throw new IllegalStateException("Path to Firebase credentials key file must be set before initialization");
        }

        try (InputStream serviceAccount = openCredentialsStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(databaseUrl)
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        }
    }

    public void setPathToKey(String pathToKey) {
        this.pathToKey = pathToKey;
    }

    private InputStream openCredentialsStream() throws IOException {
        Path filesystemPath = Paths.get(pathToKey);
        if (Files.exists(filesystemPath)) {
            return Files.newInputStream(filesystemPath);
        }

        ClassPathResource classPathResource = new ClassPathResource(pathToKey);
        if (classPathResource.exists()) {
            return classPathResource.getInputStream();
        }

        throw new IOException("Firebase credentials file not found: " + pathToKey);
    }

    public static void main(String[] args) {
        try {
            FirebaseHandler firebaseHandler = new FirebaseHandler("https://numerosity-583f5.firebaseio.com");
            firebaseHandler.setPathToKey("c:/Git/Numerosity/server/src/resources/Firebase.json");
            firebaseHandler.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
