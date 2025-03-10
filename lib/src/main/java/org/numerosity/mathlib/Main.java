package org.numerosity.mathlib;

import org.numerosity.mathlib.Subsystems.DatabaseHandler;

public class Main {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            String serviceAccountPath = "path/to/your/serviceAccountKey.json"; // Replace with the actual path
            String firebaseURL = "https://numerosity-583f5-default-rtdb.firebaseio.com/";
            String projectId = "numerosity-583f5";

            Library lib = new Library(firebaseURL, serviceAccountPath, projectId);

            // Firebase operations
            DatabaseHandler db = lib.getDatabaseHandler();
            db.createUserDocument("user123", "math_learner");
            db.incrementCorrect("user123");

            // Local database operations -- save a new question
            // lib.getLocalDbHandler().saveQuestion("questions", "q1", Map.of(
            // "text", "What's 2+2?",
            // "latex", "2 + 2 = ?",
            // "answer", 4));

            // Load question
            String directory = "src/main/java/org/numerosity/Database"; // currently gets it from questions folder
            String latex = lib.getQuestionLoader().loadAsLatex("questions", "q1");
            System.out.println("LaTeX question: " + latex);
            // Display answer
            System.out.println("Answer: " + 
                                            lib.getLocalDbHandler()
                                            .loadQuestion("questions", "q1")
                                            .get("correct_option_id"));
         
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
