package org.vaadin.numerosity.Subsystems;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class QuestionLoader {
    private final LocalDatabaseHandler localDb;
    String directory = "Database/Bank/AlgebraOne/Easy";
    String questionId = "q1";

    public QuestionLoader(LocalDatabaseHandler localDb) {
        this.localDb = localDb;
    }

    public String loadAsText() throws Exception {
        Map<String, Object> question = localDb.loadQuestion(directory, questionId);
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
    }

    public String loadAsLatex() throws Exception {
        Map<String, Object> question = localDb.loadQuestion(directory, questionId);
        if (!question.containsKey("latex")) {
            throw new IllegalArgumentException("Question has no 'latex' field");
        }
        return question.get("latex").toString();
    }
}
