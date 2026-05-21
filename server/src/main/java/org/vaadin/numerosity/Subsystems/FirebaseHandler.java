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
    /** The database URL for Firebase. */
    private String databaseUrl;
    /** The path to the Firebase credentials key file. */
    private String pathToKey;

    /**
     * Constructor that sets the database URL.
     *
     * @param databaseUrl the Firebase database URL
     */
    public FirebaseHandler(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    /**
     * Initializes Firebase with the provided credentials and database URL.
     *
     * @throws IOException if the credentials file cannot be read
     * @throws IllegalStateException if pathToKey has not been set
     */
    public void initialize() throws IOException {
        if (pathToKey == null || pathToKey.isEmpty()) {
            throw new IllegalStateException("Path to Firebase credentials key file must be set before initialization");
        }
        FileInputStream serviceAccount = new FileInputStream(pathToKey); // Use pathToKey
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl)
                .build();
        FirebaseApp.initializeApp(options);
    }

    /**
     * Sets the path to the Firebase credentials key file.
     *
     * @param pathToKey the path to the key file
     */
    public void setPathToKey(String pathToKey) {
        this.pathToKey = pathToKey;
    }

    /**
     * Main method for testing Firebase initialization.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            FirebaseHandler firebaseHandler = new FirebaseHandler("https://numerosity-583f5.firebaseio.com");
            firebaseHandler.setPathToKey("c:/Git/Numerosity/server/src/resources/Firebase.json"); // Set the path to your Firebase credentials file
            firebaseHandler.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
 * 
 * To use firebase featureset in code:
 * import com.google.firebase.auth.FirebaseAuth;
 * import com.google.firebase.auth.UserRecord;
 * 
 * UserRecord.CreateRequest request = new UserRecord.CreateRequest()
 * .setEmail("user@example.com")
 * .setPassword("secretPassword");
 * UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
 * System.out.println("Successfully created new user: " + userRecord.getUid());
 * 
 */