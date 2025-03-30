package org.vaadin.numerosity.Featureset.AppFunctions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.numerosity.Subsystems.QuestionContentLoader;

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
    private String currentQuestionId;
    private String currentDifficulty;
    private String currentSubject;

    private final QuestionContentLoader questionLoader;

    // To store question solving info: time, question ID, difficulty, subject
    private Map<String, Object> rushModeData = new HashMap<>();

    @Autowired
    public rush(QuestionContentLoader questionLoader) throws Exception {
        this.questionLoader = questionLoader;

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
            // Load the question as a Map
            Map<String, Object> questionMap = questionLoader.loadAsMap();

            // Extract question details
            currentQuestionId = (String) questionLoader.getQuestionId(questionMap);
            String questionText = (String) questionLoader.getQuestionText(questionMap);
            currentDifficulty = (String) questionLoader.getQuestionDifficulty(questionMap);
            currentSubject = questionLoader.getQuestionTags(questionMap).toString(); // Adjust if necessary

            // Load all answer choices
            Map<String, String> answerChoices = new HashMap<>();
            answerChoices.put("a", questionLoader.getAnswerChoice(questionMap, "a"));
            answerChoices.put("b", questionLoader.getAnswerChoice(questionMap, "b"));
            answerChoices.put("c", questionLoader.getAnswerChoice(questionMap, "c"));
            answerChoices.put("d", questionLoader.getAnswerChoice(questionMap, "d"));

            // Determine the correct answer key
            correctAnswerKey = questionLoader.getCorrectAnswerKey(questionMap);

            // Set question text
            questionDisplay.setText(questionText);

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

    private void handleAnswer(int index) {
        // Determine which button was pressed to answer
        String selectedAnswerKey = null;
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

        // Validate answer
        boolean isCorrect = selectedAnswerKey != null && selectedAnswerKey.equals(correctAnswerKey);

        // Store question solving information
        long currentTime = Instant.now().toEpochMilli(); // Use milliseconds for better precision
        rushModeData.put("questionSolvedAtTime", currentTime);
        rushModeData.put("questionId", currentQuestionId);
        rushModeData.put("questionDifficulty", currentDifficulty);
        rushModeData.put("questionSubject", currentSubject);

        if (isCorrect) {
            score++;
            scoreDisplay.setText("Score: " + score);
            Notification.show("Correct!");
        } else {
            Notification.show("Incorrect!");
        }

        // Load the next question
        loadQuestion();
    }
}
