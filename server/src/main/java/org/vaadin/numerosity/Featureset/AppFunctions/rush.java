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

@Route("rush")
public class rush extends VerticalLayout {

    private Div questionDisplay = new Div();
    private Button[] answerButtons = new Button[4];
    private Div scoreDisplay = new Div();
    private int score = 0;
    private String correctAnswerKey; // Store the key ("a", "b", "c", or "d") of the correct answer

    private final QuestionContentLoader questionLoader;
    private final LocalDatabaseHandler localDbHandler;
    // private final DatabaseHandler databaseHandler;
    String selectedAnswerKey = null;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Autowired
    private DatabaseHandler firebaseDataHandler;

    @Autowired
    private MainView mainView;

    @Autowired
    private DatabaseHandler databaseHandler;

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