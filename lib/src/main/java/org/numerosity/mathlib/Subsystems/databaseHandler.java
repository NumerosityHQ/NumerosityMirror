package org.numerosity.mathlib.Subsystems;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

public class DatabaseHandler {
    private final Firestore firestore;
    
    public DatabaseHandler(Firestore firestore) {
        this.firestore = firestore;
    }

    public void createUserDocument(String userId, String username) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("created_at", new Date());
        userData.put("correct", 0);
        userData.put("wrong", 0);
        userData.put("answered", 0);
        userData.put("unattempted", 0);
        
        docRef.set(userData).get();
    }

    public void updateStatistic(String userId, String field, int delta) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> updates = new HashMap<>();
        updates.put(field, FieldValue.increment(delta));
        docRef.update(updates).get();
    }

    // Add specific helper methods for common operations
    public void incrementCorrect(String userId) throws ExecutionException, InterruptedException {
        updateStatistic(userId, "correct", 1);
    }

    public void incrementWrong(String userId) throws ExecutionException, InterruptedException {
        updateStatistic(userId, "wrong", 1);
    }
}
