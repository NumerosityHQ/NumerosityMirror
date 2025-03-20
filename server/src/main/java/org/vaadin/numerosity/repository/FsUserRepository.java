package org.vaadin.numerosity.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.vaadin.numerosity.repository.exception.DbException;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

public class FsUserRepository implements UserRepository {

    private final Firestore firestore;

    public FsUserRepository(Firestore firestoneClient) { // was Firestore firestoneClient
        this.firestore = firestoneClient;
    }

    @Override
    public void createUserDocument(String userId, String username) {
        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("created_at", new Date());
        userData.put("correct", 0);
        userData.put("wrong", 0);
        userData.put("answered", 0);
        userData.put("unattempted", 0);
        try {
         docRef.set(userData).get();
        }  catch (InterruptedException | ExecutionException e) {
            throw new DbException("Exception while creating user", e);
        }
    }


    @Override
    public void incrementCorrect(String userId) {
        updateStatistic(userId, "correct", 1);

    }

    @Override
    public void incrementWrong(String userId) {
        updateStatistic(userId, "wrong", 1);
    }


    
    private void updateStatistic(String userId, String field, int delta) {
        DocumentReference docRef = firestore.collection("users").document(userId);
        Map<String, Object> updates = new HashMap<>();
        updates.put(field, FieldValue.increment(delta));
        try {
             docRef.update(updates).get();
        }  catch (InterruptedException | ExecutionException e) {
            throw new DbException("Exception while updating statistic ", e);
        }
    }

    @Override
    public Optional<Map<String, Object>> getUserStats(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserStats'");
    }

    @Override
    public boolean userExists(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'userExists'");
    }

    
}
