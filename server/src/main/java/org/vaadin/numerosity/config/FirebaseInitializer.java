package org.vaadin.numerosity.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.firebase.FirebaseApp;

import org.vaadin.numerosity.Subsystems.FirebaseHandler;

import java.io.File;
import java.io.IOException;

/**
 * Initializes Firebase application on startup.
 */
@Configuration
public class FirebaseInitializer {

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    @Value("${firebase.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        try {
            // Check if Firebase app is already initialized
            if (FirebaseApp.getApps().isEmpty()) {
                Resource resource = new ClassPathResource(credentialsPath);
                File file = resource.getFile();
                String absolutePath = file.getAbsolutePath();

                String databaseUrl = "https://" + projectId + ".firebaseio.com";

                FirebaseHandler firebaseHandler = new FirebaseHandler(databaseUrl);
                firebaseHandler.setPathToKey(absolutePath);
                firebaseHandler.initialize();
            }
        } catch (Exception e) {
            // Log the error and continue without Firebase
            System.err.println("Warning: Failed to initialize Firebase. Some features may be unavailable. Error: " + e.getMessage());
        }
    }
}