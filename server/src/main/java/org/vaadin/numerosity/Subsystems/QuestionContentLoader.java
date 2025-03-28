//  A class is needed to appropriately load text or latex content from questions
//  Create a new class to do this in:
package org.vaadin.numerosity.Subsystems;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class QuestionContentLoader {

    private final LocalDatabaseHandler localDbHandler;
    public QuestionContentLoader(LocalDatabaseHandler localDbHandler) {
        this.localDbHandler = localDbHandler;
    }

    public String loadAsText() throws Exception {
        Map<String, Object> question = localDbHandler.loadRandomQuestion();
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
    }

    public String loadAsLatex() throws Exception {
        Map<String, Object> question = localDbHandler.loadRandomQuestion();
        if (!question.containsKey("latex")) {
            throw new IllegalArgumentException("Question has no 'latex' field");
        }
        return question.get("latex").toString();
    }

    public String getAnswerChoice(String letter) throws Exception {
        //return localDbHandler.getAnswerChoiceText(localDbHandler.getChosenQuestion(), optionChoice);
        return localDbHandler.getAnswerChoiceText(localDbHandler.getChosenQuestion(), letter);
    }

    // public String getCorrectAnswerKey() {
    //     return question.get(currentQuestionId).get("correct");
    // }
    
}
