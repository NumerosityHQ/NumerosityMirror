package org.vaadin.numerosity.Featureset.AppFunctions;

import org.vaadin.numerosity.ui.views.components.QuizShell;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Vaadin view for the Zen Mode quiz interface.
 */
@Route("zen")
@AnonymousAllowed
public class zen extends VerticalLayout {

    private final VerticalLayout pageShell;
    private final Div quizPanel = QuizShell.createPanel();
    private Div questionDisplay = new Div();
    private Button[] answerButtons = new Button[4];
    private Button explanationButton = QuizShell.createSecondaryButton("Show Explanation");
    private Div explanationDiv = new Div();

    public zen() {
        this.pageShell = QuizShell.createPage(
                "Zen Mode",
                "Relaxed practice with a familiar card-based layout and room for explanations.");

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setMargin(false);
        buildView();
    }

    private void buildView() {
        quizPanel.removeAll();

        H3 header = new H3("Zen Mode");
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
                .set("grid-template-columns", "repeat(auto-fit, minmax(180px, 1fr))")
                .set("gap", "var(--lumo-space-m)");

        for (int i = 0; i < 4; i++) {
            answerButtons[i] = QuizShell.createSecondaryButton("Option " + (i + 1));
            answerButtons[i].setWidthFull();
            answerButtons[i].addClickListener(e -> handleAnswer());
            answerGrid.add(answerButtons[i]);
        }

        explanationButton.addClickListener(e -> toggleExplanation());
        explanationDiv.setVisible(false);
        explanationDiv.getStyle()
                .set("padding", "var(--lumo-space-m)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background", "var(--lumo-contrast-5pct)")
                .set("border", "1px solid var(--lumo-contrast-10pct)");
        explanationDiv.setText("Explanation goes here.");

        quizPanel.add(header, questionDisplay, answerGrid, explanationButton, explanationDiv);
        pageShell.removeAll();
        pageShell.add(quizPanel);
        add(pageShell);
        loadQuestion();
    }

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
