package org.vaadin.numerosity.Featureset.AppFunctions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.numerosity.Subsystems.DatabaseHandler;
import org.vaadin.numerosity.Subsystems.LocalDatabaseHandler;
import org.vaadin.numerosity.Subsystems.QuestionContentLoader;
import org.vaadin.numerosity.config.FirestoreAvailableCondition;
import org.vaadin.numerosity.ui.views.components.QuizShell;

import com.google.firebase.auth.FirebaseAuth;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Vaadin view for the Rush Mode quiz interface.
 */
@Route("rush")
@AnonymousAllowed
public class rush extends VerticalLayout {

    private final QuestionContentLoader questionLoader;
    private final LocalDatabaseHandler localDbHandler;
    private final VerticalLayout pageShell;
    private final Div quizPanel = QuizShell.createPanel();

    private Div questionDisplay = new Div();
    private Button[] answerButtons = new Button[4];
    private Div scoreDisplay = new Div();
    private int score = 0;
    private String correctAnswerKey;
    private String selectedAnswerKey = null;
    private FirebaseAuth firebaseAuth;

    @Autowired(required = false)
    private DatabaseHandler databaseHandler;

    public rush(QuestionContentLoader questionLoader, LocalDatabaseHandler localDbHandler) {
        this.questionLoader = questionLoader;
        this.localDbHandler = localDbHandler;
        this.pageShell = QuizShell.createPage(
                "Rush Mode",
                "Fast-paced practice with the same visual language as the rest of the app.");

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setMargin(false);
        buildView();
    }

    private void buildView() {
        quizPanel.removeAll();

        H3 header = new H3("Rush Mode");
        header.getStyle().set("margin", "0");

        questionDisplay.getStyle()
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("padding", "var(--lumo-space-m)")
                .set("background", "var(--lumo-base-color)")
                .set("min-height", "200px")
                .set("margin-bottom", "var(--lumo-space-m)");

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

        scoreDisplay.setText("Score: 0");
        scoreDisplay.getStyle().set("font-weight", "600");

        quizPanel.add(header, questionDisplay, answerGrid, scoreDisplay);
        pageShell.removeAll();
        pageShell.add(quizPanel);
        add(pageShell);
        loadQuestion();
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
            scoreDisplay.setText("Score: " + score);
            Notification.show("Correct!");
        } else {
            Notification.show("Incorrect!");
        }

        loadQuestion();
    }
}
