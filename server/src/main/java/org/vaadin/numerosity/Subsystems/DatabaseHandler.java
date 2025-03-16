package org.vaadin.numerosity.Subsystems;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

import org.springframework.stereotype.Service;

@Service
public class DatabaseHandler {
    private final Firestore firestore;
    private String userId;
    private String username;    

    public DatabaseHandler(Firestore firestore) {
        this.firestore = firestore;
    }

    public void createUserDocument(String userId, String username) throws ExecutionException, InterruptedException {
        this.userId = userId;
        this.username = username;
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

    public void updateStatistic(String userId, String field, int delta)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> updates = new HashMap<>();
        updates.put(field, FieldValue.increment(delta));
        docRef.update(updates).get();
    }

    public void incrementCorrect() {
        try {
            updateStatistic(userId, "correct", 1);
        } catch (ExecutionException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void incrementWrong() {
        try {
            updateStatistic(userId, "wrong", 1);
        } catch (ExecutionException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
