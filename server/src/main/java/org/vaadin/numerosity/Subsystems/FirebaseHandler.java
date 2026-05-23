package org.vaadin.numerosity.Subsystems;

import java.io.FileInputStream;
import java.io.IOException;

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
        FileInputStream serviceAccount = new FileInputStream(pathToKey);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl)
                .build();
        FirebaseApp.initializeApp(options);
    }

    public void setPathToKey(String pathToKey) {
        this.pathToKey = pathToKey;
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