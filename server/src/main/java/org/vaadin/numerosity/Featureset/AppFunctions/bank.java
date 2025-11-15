package org.vaadin.numerosity.Featureset.AppFunctions;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Vaadin view for the Bank Mode quiz interface.
 * Allows users to select quiz parameters and start a session with multiple questions.
 */
@Route("bank")
public class bank extends VerticalLayout {

    /** Combo box for selecting the subject. */
    private ComboBox<String> subjectSelector = new ComboBox<>("Subject");
    /** Combo box for selecting the difficulty level. */
    private ComboBox<String> difficultySelector = new ComboBox<>("Difficulty");
    /** Combo box for selecting the number of questions. */
    private ComboBox<Integer> questionCountSelector = new ComboBox<>("Number of Questions");
    /** Button to start the quiz session. */
    private Button startButton = new Button("Start Session");
    /** Div for displaying instructions and messages. */
    private Div instructionsDiv = new Div();

    /**
     * Constructor that initializes the Bank Mode UI.
     * Sets up selectors for subject, difficulty, question count, and start button.
     */
    public bank() {
        setSizeFull();

        // Header
        H2 header = new H2("Bank Mode");
        add(header);

        // Subject Selector
        subjectSelector.setItems("Algebra I", "Algebra II", "Calculus", "Geometry", "Precalculus");
        subjectSelector.setPlaceholder("Select Subject");

        // Difficulty Selector
        difficultySelector.setItems("Easy", "Medium", "Hard");
        difficultySelector.setPlaceholder("Select Difficulty");

        // Question Count Selector
        questionCountSelector.setItems(5, 10, 15, 20);
        questionCountSelector.setPlaceholder("Select Number of Questions");

        // Instructions
        instructionsDiv.setText("Choose your preferences and click 'Start Session' to begin.");
        instructionsDiv.getStyle().set("margin-top", "20px");

        // Start Button
        startButton.addClickListener(e -> startSession());

        add(subjectSelector, difficultySelector, questionCountSelector, instructionsDiv, startButton);
    }

    /**
     * Validates user selections and initiates the quiz session.
     * Displays error message if selections are incomplete, otherwise confirms session start.
     */
    private void startSession() {
        String subject = subjectSelector.getValue();
        String difficulty = difficultySelector.getValue();
        Integer questionCount = questionCountSelector.getValue();

        if (subject == null || difficulty == null || questionCount == null) {
            instructionsDiv.setText("Please select all options before starting.");
            return;
        }

        instructionsDiv.setText(String.format("Starting session: %s (%s) - %d questions.", subject, difficulty, questionCount));
    }
}
