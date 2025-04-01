//  A class is needed to appropriately load text or latex content from questions
//  Create a new class to do this in:
package org.vaadin.numerosity.Subsystems;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.vaadin.numerosity.Application;

@Service
public class QuestionContentLoader {

    private final Application application;

    private final LocalDatabaseHandler localDbHandler;
    private Map<String, Object> chosenQuestionMap;
    public QuestionContentLoader(LocalDatabaseHandler localDbHandler, Application application) {
        this.localDbHandler = localDbHandler;
        this.application = application;
    }

    public String loadRandomAsText() throws Exception {
        Map<String, Object> question = localDbHandler.loadRandomQuestion();
        chosenQuestionMap = question;
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
    }

    public String loadRandomAsLatex() throws Exception {
        Map<String, Object> question = localDbHandler.loadRandomQuestion();
        if (!question.containsKey("latex")) {
            throw new IllegalArgumentException("Question has no 'latex' field");
        }
        return question.get("latex").toString();
    }

    public String loadAsText(String questionId, String databasePath) throws Exception {
        Map<String, Object> question = localDbHandler.loadSpecificQuestion(questionId, databasePath);
        chosenQuestionMap = question;
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
    }

    public String loadAsLatex(String questionId, String databasePath) throws Exception {
        Map<String, Object> question = localDbHandler.loadSpecificQuestion(questionId, databasePath);
        chosenQuestionMap = question;
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
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

    public String getCurrentQuestionId() throws Exception {
        if (chosenQuestionMap == null || !chosenQuestionMap.containsKey("question_id")) {
            throw new IllegalStateException("No 'question_id' found for the chosen question.");
        }
        return (String) chosenQuestionMap.get("question_id");
    }
    
}
