package org.numerosity.mathlib.Subsystems;

import java.util.Map;

public class QuestionLoader {
    private final LocalDatabaseHandler localDb;

    public QuestionLoader(LocalDatabaseHandler localDb) {
        this.localDb = localDb;
    }

    public String loadAsText(String directory, String questionId) throws Exception {
        Map<String, Object> question = localDb.loadQuestion(directory, questionId);
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
    }

    public String loadAsLatex(String directory, String questionId) throws Exception {
        Map<String, Object> question = localDb.loadQuestion(directory, questionId);
        if (!question.containsKey("latex")) {
            throw new IllegalArgumentException("Question has no 'latex' field");
        }
        return question.get("latex").toString();
    }
}

