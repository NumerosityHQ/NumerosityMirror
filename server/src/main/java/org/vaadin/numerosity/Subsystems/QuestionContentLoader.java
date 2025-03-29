//  A class is needed to appropriately load text or latex content from questions
//  Create a new class to do this in:
package org.vaadin.numerosity.Subsystems;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class QuestionContentLoader {

    private final LocalDatabaseHandler localDbHandler;
    private Map<String, Object> chosenQuestionMap;
    public QuestionContentLoader(LocalDatabaseHandler localDbHandler) {
        this.localDbHandler = localDbHandler;
    }

    public String loadAsText() throws Exception {
        Map<String, Object> question = localDbHandler.loadRandomQuestion();
        chosenQuestionMap = question;
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
    //     return chosenQuestionMap.get(localDbHandler.getChosenQuestion()).get("correct_option_id");
    // }
    
    public String getCorrectAnswerKey() throws Exception{
        // Map<String, String> questionData = chosenQuestionMap.get(localDbHandler.getChosenQuestionMap());
        if (chosenQuestionMap == null || !chosenQuestionMap.containsKey("correct_option_id")) {
            throw new IllegalStateException("No 'correct_option_id' answer found for the chosen question.");
        }
        return (String) chosenQuestionMap.get("correct_option_id");
    }
    
}
