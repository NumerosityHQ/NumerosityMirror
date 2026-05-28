package org.vaadin.numerosity.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.firebase.FirebaseApp;

import org.vaadin.numerosity.Subsystems.FirebaseHandler;

/**
 * Initializes Firebase application on startup.
 */
@Configuration
public class FirebaseInitializer {

    private static final Logger log = LoggerFactory.getLogger(FirebaseInitializer.class);

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    @Value("${firebase.project.id}")
    private String projectId;

    @PostConstruct
    public void init() {
        try {
            if (FirebaseConfigurationSupport.isDemoMode(credentialsPath, projectId)) {
                log.info("Firebase configuration is empty. Demo mode is active and Firebase will not be initialized.");
                return;
            }

            if (FirebaseApp.getApps().isEmpty()) {
                String databaseUrl = "https://" + projectId + ".firebaseio.com";

                FirebaseHandler firebaseHandler = new FirebaseHandler(databaseUrl);
                firebaseHandler.setPathToKey(credentialsPath);
                firebaseHandler.initialize();
            }
        } catch (Exception e) {
            log.warn("Failed to initialize Firebase. Some features may be unavailable. Error: {}", e.getMessage());
        }
    }
}
