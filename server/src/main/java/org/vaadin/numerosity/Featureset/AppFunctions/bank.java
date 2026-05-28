package org.vaadin.numerosity.Featureset.AppFunctions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.numerosity.Subsystems.DatabaseHandler;
import org.vaadin.numerosity.Subsystems.LocalDatabaseHandler;
import org.vaadin.numerosity.Subsystems.QuestionContentLoader;
import org.vaadin.numerosity.ui.views.components.QuizShell;

import com.google.firebase.auth.FirebaseAuth;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Vaadin view for the Bank Mode quiz interface.
 */
@Route("bank")
@RouteAlias("practice")
@AnonymousAllowed
public class bank extends VerticalLayout {

    private final QuestionContentLoader questionLoader;
    private final LocalDatabaseHandler localDbHandler;
    private final VerticalLayout pageShell;
    private final Div quizPanel = QuizShell.createPanel();

    private ComboBox<String> subjectSelector = new ComboBox<>("Subject");
    private ComboBox<String> difficultySelector = new ComboBox<>("Difficulty");
    private ComboBox<Integer> questionCountSelector = new ComboBox<>("Number of Questions");
    private Button startButton = QuizShell.createPrimaryButton("Start Session");
    private Div instructionsDiv = new Div();
    private Div questionDisplay = new Div();
    private Button[] answerButtons = new Button[4];
    private Div scoreDisplay = new Div();
    private int score = 0;
    private String correctAnswerKey;
    private String selectedAnswerKey = null;
    private int questionsRemaining = 0;
    private int totalQuestions = 0;
    private FirebaseAuth firebaseAuth;

    @Autowired(required = false)
    private DatabaseHandler databaseHandler;

    public bank(QuestionContentLoader questionLoader, LocalDatabaseHandler localDbHandler) {
        this.questionLoader = questionLoader;
        this.localDbHandler = localDbHandler;
        this.pageShell = QuizShell.createPage(
                "Bank Mode",
                "Choose a subject, difficulty, and question count for a full practice session.");

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setMargin(false);

        buildSelectionView();
    }

    private void buildSelectionView() {
        instructionsDiv.setText("Choose your preferences and click Start Session to begin.");
        instructionsDiv.getStyle().set("color", "var(--lumo-secondary-text-color)");

        subjectSelector.setItems("Algebra I", "Algebra II", "Calculus", "Geometry", "Precalculus");
        subjectSelector.setPlaceholder("Select Subject");
        difficultySelector.setItems("Easy", "Medium", "Hard");
        difficultySelector.setPlaceholder("Select Difficulty");
        questionCountSelector.setItems(5, 10, 15, 20);
        questionCountSelector.setPlaceholder("Select Number of Questions");

        startButton.addClickListener(e -> startSession());

        Div controls = new Div();
        controls.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(auto-fit, minmax(220px, 1fr))")
                .set("gap", "var(--lumo-space-m)");
        controls.add(subjectSelector, difficultySelector, questionCountSelector);

        quizPanel.removeAll();
        quizPanel.add(controls, instructionsDiv, startButton);

        pageShell.removeAll();
        pageShell.add(quizPanel);
        add(pageShell);
    }

    private void startSession() {
        String subject = subjectSelector.getValue();
        String difficulty = difficultySelector.getValue();
        Integer questionCount = questionCountSelector.getValue();

        if (subject == null || difficulty == null || questionCount == null) {
            instructionsDiv.setText("Please select all options before starting.");
            return;
        }

        setDirectoryFromSelection(subject, difficulty);
        score = 0;
        questionsRemaining = questionCount;
        totalQuestions = questionCount;

        quizPanel.removeAll();

        H3 sessionHeader = new H3(String.format("%s (%s) - %d questions", subject, difficulty, questionCount));
        sessionHeader.getStyle().set("margin", "0");

        questionDisplay.getStyle()
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("padding", "var(--lumo-space-m)")
                .set("background", "var(--lumo-base-color)")
                .set("min-height", "150px");
        questionDisplay.setWidthFull();

        scoreDisplay.setText("Score: 0 / " + totalQuestions);
        scoreDisplay.getStyle().set("font-weight", "600");

        Div answerGrid = new Div();
        answerGrid.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(auto-fit, minmax(220px, 1fr))")
                .set("gap", "var(--lumo-space-m)");

        for (int i = 0; i < 4; i++) {
            answerButtons[i] = QuizShell.createSecondaryButton("Option " + (i + 1));
            final int index = i;
            answerButtons[i].setWidthFull();
            answerButtons[i].addClickListener(e -> {
                try {
                    handleAnswer(index);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            answerGrid.add(answerButtons[i]);
        }

        quizPanel.add(sessionHeader, questionDisplay, answerGrid, scoreDisplay);
        loadQuestion();
    }

    private void setDirectoryFromSelection(String subject, String difficulty) {
        localDbHandler.setDirectory("all");
    }

    private void loadQuestion() {
        try {
            String question = questionLoader.loadRandomAsText();
            questionDisplay.setText(question);

            Map<String, String> answerChoices = new HashMap<>();
            answerChoices.put("a", questionLoader.getAnswerChoice("a"));
            answerChoices.put("b", questionLoader.getAnswerChoice("b"));
            answerChoices.put("c", questionLoader.getAnswerChoice("c"));
            answerChoices.put("d", questionLoader.getAnswerChoice("d"));

            correctAnswerKey = questionLoader.getCorrectAnswerKey();

            answerButtons[0].setText(answerChoices.get("a"));
            answerButtons[1].setText(answerChoices.get("b"));
            answerButtons[2].setText(answerChoices.get("c"));
            answerButtons[3].setText(answerChoices.get("d"));
        } catch (Exception e) {
            questionDisplay.setText("Error loading question: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleAnswer(int index) throws Exception {
        switch (index) {
            case 0 -> selectedAnswerKey = "a";
            case 1 -> selectedAnswerKey = "b";
            case 2 -> selectedAnswerKey = "c";
            case 3 -> selectedAnswerKey = "d";
        }

        if (selectedAnswerKey.equals(correctAnswerKey)) {
            score++;
            scoreDisplay.setText("Score: " + score + " / " + totalQuestions);
            Notification.show("Correct!");
        } else {
            Notification.show("Incorrect!");
        }

        questionsRemaining--;
        if (questionsRemaining <= 0) {
            questionDisplay.setText("Quiz Complete! Final Score: " + score + " / " + totalQuestions);
            for (Button btn : answerButtons) {
                btn.setEnabled(false);
            }
            return;
        }

        loadQuestion();
    }
}
