package org.vaadin.numerosity.Featureset.AppFunctions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.numerosity.MainView;
import org.vaadin.numerosity.Subsystems.DatabaseHandler;
import org.vaadin.numerosity.Subsystems.LocalDatabaseHandler;
import org.vaadin.numerosity.Subsystems.QuestionContentLoader;

import com.google.firebase.auth.FirebaseAuth;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Vaadin view for the Rush Mode quiz interface.
 * Presents questions quickly with scoring.
 */
@Route("rush")
public class rush extends VerticalLayout {

    /** Div for displaying the question. */
    private Div questionDisplay = new Div();
    /** Array of buttons for answer choices. */
    private Button[] answerButtons = new Button[4];
    /** Div for displaying the score. */
    private Div scoreDisplay = new Div();
    /** Current score. */
    private int score = 0;
    /** The key of the correct answer. */
    private String correctAnswerKey; // Store the key ("a", "b", "c", or "d") of the correct answer

    /** Injected QuestionContentLoader. */
    private final QuestionContentLoader questionLoader;
    /** Injected LocalDatabaseHandler. */
    private final LocalDatabaseHandler localDbHandler;
    // private final DatabaseHandler databaseHandler;
    /** The selected answer key. */
    String selectedAnswerKey = null;

    /** Firebase Auth instance. */
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    /** Injected DatabaseHandler. */
    @Autowired
    private DatabaseHandler firebaseDataHandler;

    /** Injected MainView. */
    @Autowired
    private MainView mainView;

    /** Injected DatabaseHandler. */
    @Autowired
    private DatabaseHandler databaseHandler;

    /**
     * Constructor injecting dependencies and setting up the UI.
     *
     * @param questionLoader the question content loader
     * @param localDbHandler the local database handler
     * @param databaseHandler the database handler
     * @throws Exception if initialization fails
     */
    @Autowired
    public rush(QuestionContentLoader questionLoader, LocalDatabaseHandler localDbHandler, DatabaseHandler databaseHandler) throws Exception {
        this.questionLoader = questionLoader;
        this.localDbHandler = localDbHandler;
        this.databaseHandler = databaseHandler;

        setSizeFull();

        // Header
        H2 header = new H2("Rush Mode");
        add(header);

        // Question Display
        questionDisplay.getStyle().set("border", "1px solid black");
        questionDisplay.setHeight("200px");
        add(questionDisplay);

        // Answer Buttons
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new Button();
            final int index = i;
            answerButtons[i].addClickListener(e -> {
                try {
                    handleAnswer(index);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            add(answerButtons[i]);
        }

        // Score Display
        scoreDisplay.setText("Score: 0");
        add(scoreDisplay);

        loadQuestion();
    }

    /**
     * Loads a new question and updates the UI.
     */
    private void loadQuestion() {
        try {
            String question = questionLoader.loadRandomAsText();
            questionDisplay.setText(question);

            // Load all answer choices
            Map<String, String> answerChoices = new HashMap<>();
            answerChoices.put("a", questionLoader.getAnswerChoice("a"));
            answerChoices.put("b", questionLoader.getAnswerChoice("b"));
            answerChoices.put("c", questionLoader.getAnswerChoice("c"));
            answerChoices.put("d", questionLoader.getAnswerChoice("d"));

            // Determine the correct answer key
            correctAnswerKey = questionLoader.getCorrectAnswerKey();

            // Set button text
            answerButtons[0].setText(answerChoices.get("a"));
            answerButtons[1].setText(answerChoices.get("b"));
            answerButtons[2].setText(answerChoices.get("c"));
            answerButtons[3].setText(answerChoices.get("d"));

        } catch (Exception e) {
            questionDisplay.setText("Error loading question: " + e.getMessage());
            e.printStackTrace(); // Log the error for debugging
        }
    }

    // // Call these methods at appropriate places in your code, e.g., after a user
    // answers a question
    // databaseHandler.saveQuestionData(questionId, userId, answerId, isCorrect);
    // databaseHandler.incrementCorrect(userId);
    // databaseHandler.incrementWrong(userId);

   // String userId = databaseHandler.getUserId();

    private void handleAnswer(int index) throws Exception {
        // Determine which button was pressed to answer

        switch (index) {
            case 0:
                selectedAnswerKey = "a";
                break;
            case 1:
                selectedAnswerKey = "b";
                break;
            case 2:
                selectedAnswerKey = "c";
                break;
            case 3:
                selectedAnswerKey = "d";
                break;
        }

        // Check if the answer is correct and update score
        if (selectedAnswerKey.equals(correctAnswerKey)) {
            score++;
          //  databaseHandler.incrementCorrect(userId);
            scoreDisplay.setText("Score: " + score);
            Notification.show("Correct!");
        } else {
          //  databaseHandler.incrementWrong(userId);
            Notification.show("Incorrect!");
        }

        // Save user answers
        Map<String, Object> userAnswers = new HashMap<>();
        userAnswers.put("question", questionDisplay.getText());
        userAnswers.put("selectedAnswer", selectedAnswerKey);
        userAnswers.put("correctAnswer", correctAnswerKey);

        // log answer first
        // databaseHandler.saveQuestionData(questionLoader.getCurrentQuestionId(), userId, selectedAnswerKey,
        //         selectedAnswerKey.equals(correctAnswerKey));

        // Load next question
        loadQuestion();
    }
}