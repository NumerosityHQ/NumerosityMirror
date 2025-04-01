package org.vaadin.numerosity;

import org.vaadin.numerosity.Featureset.AppFunctions.bank;
import org.vaadin.numerosity.Featureset.AppFunctions.rush;
import org.vaadin.numerosity.Featureset.AppFunctions.zen;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;

public class MainView extends VerticalLayout {

    public MainView() {

        // Navigation Links
        Div navigationBar = new Div();
        navigationBar.getStyle().set("width", "200px");
        navigationBar.getStyle().set("border-right", "1px solid black");
        navigationBar.getStyle().set("height", "100%");
        navigationBar.getStyle().set("position", "fixed");

        Button loginButton = new Button("Login/Signup");
        loginButton.addClickListener(event -> {
            Dialog dialog = new Dialog();

            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.add(new Label("Login/Signup"));

            TextField usernameField = new TextField("Username");
            TextField passwordField = new TextField("Password");
            dialogLayout.add(usernameField, passwordField);

            Button submitButton = new Button("Submit", e -> {
                // Handle login/signup logic here
                dialog.close();
            });
            dialogLayout.add(submitButton);

            dialog.add(dialogLayout);
            dialog.open();
        });

        navigationBar.add(loginButton);

        // Header
        H1 header = new H1("Numerosity");
        header.getStyle().set("text-align", "center");
        add(header);

        // Add navigation links to the existing navigationBar
        RouterLink practiceLink = new RouterLink("Practice", bank.class);
        RouterLink rushLink = new RouterLink("Rush", rush.class);
        RouterLink dailyLink = new RouterLink("Daily", zen.class);

        navigationBar.add(practiceLink, rushLink, dailyLink);

        // Main Content Area
        Div contentArea = new Div();
        contentArea.getStyle().set("margin-left", "220px");
        contentArea.getStyle().set("padding", "20px");

        H2 welcomeMessage = new H2("Welcome to Numerosity!");
        welcomeMessage.getStyle().set("text-align", "center");
        contentArea.add(welcomeMessage);

        HorizontalLayout layout = new HorizontalLayout(navigationBar, contentArea);
        layout.setSizeFull();

        add(layout);
    }
}