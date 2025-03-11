package org.numerosity.mathlib;

import java.io.IOException;

import org.numerosity.mathlib.Subsystems.DatabaseHandler;
import org.numerosity.mathlib.Subsystems.FirebaseHandler;
import org.numerosity.mathlib.Subsystems.FirestoneClient;
import org.numerosity.mathlib.Subsystems.LocalDatabaseHandler;
import org.numerosity.mathlib.Subsystems.QuestionLoader;
// import org.numerosity.mathlib.subsystems.FirestoneClient;
import org.numerosity.mathlib.Subsystems.ResponseHandler;

public class Library {
    private final FirebaseHandler firebaseHandler;
    private final DatabaseHandler dbHandler;
    private final LocalDatabaseHandler localDbHandler;
    private final QuestionLoader questionLoader;

    public Library(String firebaseUrl, String serviceAccountPath, String projectId) throws IOException {
        if (firebaseUrl == null || serviceAccountPath == null) {
            throw new IllegalArgumentException("Firebase URL and service account path must be provided");
        }

        serviceAccountPath = "lib/keys/firebase.json/";
        firebaseUrl =  "https://numerosity-583f5-default-rtdb.firebaseio.com/";
        projectId = "numerosity-583f5";

        // Initialize Firebase
        firebaseHandler = new FirebaseHandler(firebaseUrl);
      //  firebaseHandler.setDatabaseUrl(firebaseUrl);
        firebaseHandler.setPathToKey(serviceAccountPath);
        firebaseHandler.initialize();

        // Initialize Firestore
        FirestoneClient.initialize(projectId, serviceAccountPath);

        // Initialize handlers
        dbHandler = new DatabaseHandler(FirestoneClient.getFirestore());
        localDbHandler = new LocalDatabaseHandler();
        questionLoader = new QuestionLoader(localDbHandler);
    }

    public DatabaseHandler getDatabaseHandler() {
        return dbHandler;
    }

    public LocalDatabaseHandler getLocalDbHandler() {
        return localDbHandler;
    }

    public QuestionLoader getQuestionLoader() {
        return questionLoader;
    }

    public boolean libraryCheck() {
        return true;
    }

    public ResponseHandler getResponseHandler() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResponseHandler'");
    }
}
