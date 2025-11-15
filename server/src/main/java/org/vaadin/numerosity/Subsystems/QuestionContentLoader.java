//  A class is needed to appropriately load text or latex content from questions
//  Create a new class to do this in:
package org.vaadin.numerosity.Subsystems;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.vaadin.numerosity.Application;

/**
 * Service class for loading question content from the local database.
 * Handles loading questions in text or LaTeX format and retrieving answer choices.
 */
@Service
public class QuestionContentLoader {

    /** The application instance. */
    private final Application application;

    /** The local database handler. */
    private final LocalDatabaseHandler localDbHandler;
    /** The currently chosen question map. */
    private Map<String, Object> chosenQuestionMap;

    /**
     * Constructor injecting dependencies.
     *
     * @param localDbHandler the local database handler
     * @param application the application instance
     */
    public QuestionContentLoader(LocalDatabaseHandler localDbHandler, Application application) {
        this.localDbHandler = localDbHandler;
        this.application = application;
    }

    /**
     * Loads a random question as text.
     *
     * @return the question text
     * @throws Exception if loading fails
     */
    public String loadRandomAsText() throws Exception {
        Map<String, Object> question = localDbHandler.loadRandomQuestion();
        chosenQuestionMap = question;
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
    }

    /**
     * Loads a random question as LaTeX.
     *
     * @return the question LaTeX
     * @throws Exception if loading fails
     */
    public String loadRandomAsLatex() throws Exception {
        Map<String, Object> question = localDbHandler.loadRandomQuestion();
        if (!question.containsKey("latex")) {
            throw new IllegalArgumentException("Question has no 'latex' field");
        }
        return question.get("latex").toString();
    }

    /**
     * Loads a specific question as text.
     *
     * @param questionId the question ID
     * @param databasePath the database path
     * @return the question text
     * @throws Exception if loading fails
     */
    public String loadAsText(String questionId, String databasePath) throws Exception {
        Map<String, Object> question = localDbHandler.loadSpecificQuestion(questionId, databasePath);
        chosenQuestionMap = question;
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
    }

    /**
     * Loads a specific question as LaTeX.
     *
     * @param questionId the question ID
     * @param databasePath the database path
     * @return the question LaTeX
     * @throws Exception if loading fails
     */
    public String loadAsLatex(String questionId, String databasePath) throws Exception {
        Map<String, Object> question = localDbHandler.loadSpecificQuestion(questionId, databasePath);
        chosenQuestionMap = question;
        if (!question.containsKey("text")) {
            throw new IllegalArgumentException("Question has no 'text' field");
        }
        return question.get("text").toString();
    }

    /**
     * Gets the answer choice text for the given letter.
     *
     * @param letter the answer letter (a, b, c, d)
     * @return the answer choice text
     * @throws Exception if retrieval fails
     */
    public String getAnswerChoice(String letter) throws Exception {
        // return localDbHandler.getAnswerChoiceText(localDbHandler.getChosenQuestion(),
        // optionChoice);
        return localDbHandler.getAnswerChoiceText(localDbHandler.getChosenQuestion(), letter);
    }

    // public String getCorrectAnswerKey() {
    // return
    // chosenQuestionMap.get(localDbHandler.getChosenQuestion()).get("correct_option_id");
    // }

    /**
     * Gets the correct answer key for the chosen question.
     *
     * @return the correct answer key
     * @throws Exception if no question is chosen
     */
    public String getCorrectAnswerKey() throws Exception {
        // Map<String, String> questionData =
        // chosenQuestionMap.get(localDbHandler.getChosenQuestionMap());
        if (chosenQuestionMap == null || !chosenQuestionMap.containsKey("correct_option_id")) {
            throw new IllegalStateException("No 'correct_option_id' answer found for the chosen question.");
        }
        return (String) chosenQuestionMap.get("correct_option_id");
    }

    /**
     * Gets the current question ID.
     *
     * @return the question ID
     * @throws Exception if no question is chosen
     */
    public String getCurrentQuestionId() throws Exception {
        if (chosenQuestionMap == null || !chosenQuestionMap.containsKey("question_id")) {
            throw new IllegalStateException("No 'question_id' found for the chosen question.");
        }
        return (String) chosenQuestionMap.get("question_id");
    }

}
