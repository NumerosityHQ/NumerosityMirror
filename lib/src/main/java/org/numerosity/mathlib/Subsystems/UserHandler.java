package org.numerosity.mathlib.Subsystems;

import java.util.concurrent.ExecutionException;
import org.numerosity.mathlib.Subsystems.DatabaseHandler;

// create user, delete user, update user, get user and everything user related (web interferace sign up calls this)
public class UserHandler {
    DatabaseHandler db = new DatabaseHandler(FirestoneClient.getFirestore());

    public void createUser(String username) {
        try {
            db.createUserDocument(username, username);
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("User creation failed");
        }
    }
}
