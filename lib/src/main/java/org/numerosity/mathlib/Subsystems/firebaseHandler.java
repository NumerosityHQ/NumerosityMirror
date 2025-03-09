package org.numerosity.mathlib.Subsystems;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class firebaseHandler {
    private String databaseUrl;
    private String pathToKey;

    public void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("lib/keys/firebase.json");
       // FileInputStream serviceAccoutn = new FileInputStream("pathToKey");
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl(databaseUrl) // https://numerosity-583f5-default-rtdb.firebaseio.com/
            .build();
        FirebaseApp.initializeApp(options);
    }
    
    public void setDatabaseUrl(String url) {
        databaseUrl = url;    
    }

    public void setPathToKey(String path) {
        pathToKey = path;
    }
}

/*

 To use firebase featureset in code:
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.UserRecord;

 UserRecord.CreateRequest request = new UserRecord.CreateRequest()
    .setEmail("user@example.com")
    .setPassword("secretPassword");
 UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
 System.out.println("Successfully created new user: " + userRecord.getUid());
 
 */