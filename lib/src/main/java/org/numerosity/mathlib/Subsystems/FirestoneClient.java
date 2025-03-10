package org.numerosity.mathlib.Subsystems;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

/*
 * public class FirestoreClient { // Renamed class
    private static Firestore firestoreInstance;

    public static synchronized void initialize(String projectId, String credentialsPath) throws IOException {
        if (firestoreInstance != null) return;
        // Rest of the code remains the same
    }
}

 */
public class FirestoneClient {

    private static Firestore firestoreInstance;

    /**
     * Initializes the Firestore client with the given project ID and credentials file.
     *
     * @param projectId The Google Cloud project ID.
     * @param credentialsPath The path to the service account key JSON file.
     * @throws IOException If there is an issue reading the credentials file.
     */
    public static synchronized void initialize(String projectId, String credentialsPath) throws IOException {
        if (firestoreInstance != null) return;
        if (firestoreInstance == null) {
            FileInputStream serviceAccount = new FileInputStream(credentialsPath);

            FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            firestoreInstance = firestoreOptions.getService();
        }
    }

    /**
     * Returns the initialized Firestore instance.
     *
     * @return The Firestore instance.
     * @throws IllegalStateException If the client has not been initialized.
     */
    public static Firestore getFirestore() {
        if (firestoreInstance == null) {
            throw new IllegalStateException("FirestoreClient has not been initialized. Call initialize() first.");
        }
        return firestoreInstance;
    }
}
