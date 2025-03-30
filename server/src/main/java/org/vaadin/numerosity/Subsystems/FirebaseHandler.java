package org.vaadin.numerosity.Subsystems;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.cloud.firestore.Firestore;

public class FirebaseHandler {

    @Autowired
    private Firestore firestore;

    public void saveResponse(String userId, Map<String, Object> responseLog) {
        try {
            firestore.collection("responses").document(userId).set(responseLog);
            System.out.println("Responses saved successfully!");
        } catch (Exception e) {
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