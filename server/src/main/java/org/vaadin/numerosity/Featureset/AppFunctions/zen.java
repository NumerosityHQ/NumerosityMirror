package org.vaadin.numerosity.Featureset.AppFunctions;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Vaadin view for the Zen Mode quiz interface.
 * Allows relaxed question answering with explanations.
 */
@Route("zen")
public class zen extends VerticalLayout {

    /** Div for displaying the question. */
    private Div questionDisplay = new Div();
    /** Array of buttons for answer choices. */
    private Button[] answerButtons = new Button[4];
    /** Button to show/hide explanation. */
    private Button explanationButton = new Button("Show Explanation");
    /** Div for displaying the explanation. */
    private Div explanationDiv = new Div();

    /**
     * Constructor that sets up the UI for Zen mode.
     */
    public zen() {
        setSizeFull();

        // Header
        H2 header = new H2("Zen Mode");
        add(header);

        // Question Display
        questionDisplay.getStyle().set("border", "1px solid black");
        questionDisplay.setHeight("200px");
        add(questionDisplay);

        // Answer Buttons
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new Button("Option " + (i + 1));
            answerButtons[i].addClickListener(e -> handleAnswer());
            add(answerButtons[i]);
        }

        // Explanation Section
        explanationButton.addClickListener(e -> toggleExplanation());
        
	add(explanationButton);
	explanationDiv.setVisible(false);
	explanationDiv.setText("Explanation goes here.");
	add(explanationDiv);

	loadQuestion();
}

    /**
     * Loads a sample question.
     */
private void loadQuestion() {
	questionDisplay.setText("Sample Question: What is the square root of 16?");
}

private void handleAnswer() {
	Notification.show("Answer recorded. No scoring in Zen mode.");
}

private void toggleExplanation() {
	explanationDiv.setVisible(!explanationDiv.isVisible());
}
}
