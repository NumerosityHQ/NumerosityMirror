package org.vaadin.numerosity;

import org.vaadin.numerosity.Featureset.AppFunctions.bank;
import org.vaadin.numerosity.Featureset.AppFunctions.rush;
import org.vaadin.numerosity.Featureset.AppFunctions.zen;
import org.vaadin.numerosity.Subsystems.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.notification.Notification;

import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

/**
 * Main view of the Vaadin application, serving as the home page.
 * Provides navigation and login/signup functionality.
 */
@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    private LoginHandler loginHandler;

    /**
     * Constructor that sets up the UI components for the main view.
     */
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

            TextField emailField = new TextField("Email");
            PasswordField passwordField = new PasswordField("Password");
            dialogLayout.add(emailField, passwordField);

            Button loginSubmitButton = new Button("Login", e -> {
                String email = emailField.getValue();
                String password = passwordField.getValue();
                try {
                    String result = loginHandler.login(email, password);
                    if (result.startsWith("Login failed")) {
                        Notification.show(result);
                    } else {
                        Notification.show("Login successful! Welcome, " + email);
                        dialog.close();
                    }
                } catch (ExecutionException | InterruptedException ex) {
                    Notification.show("Login failed: " + ex.getMessage());
                }
            });
            dialogLayout.add(loginSubmitButton);
            
            

            Button signupSubmitButton = new Button("Signup", e -> {
                String email = emailField.getValue();
                String password = passwordField.getValue();
                try {
                    String result = loginHandler.signup(email, password);
                    if (result.startsWith("Signup failed")) {
                        Notification.show(result);
                    } else {
                        Notification.show("Signup successful! " + result);
                        dialog.close();
                    }
                } catch (ExecutionException | InterruptedException ex) {
                    Notification.show("Signup failed: " + ex.getMessage());
                }
            });

            
            
            dialogLayout.add(signupSubmitButton);

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