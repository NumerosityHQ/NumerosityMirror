package org.vaadin.numerosity.Subsystems;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseHandler {
    private String databaseUrl;
    private String pathToKey;

    public FirebaseHandler(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(pathToKey); // Use pathToKey
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