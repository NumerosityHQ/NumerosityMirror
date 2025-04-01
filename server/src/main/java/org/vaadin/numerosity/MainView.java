package org.vaadin.numerosity;

import org.vaadin.numerosity.Featureset.AppFunctions.bank;
import org.vaadin.numerosity.Featureset.AppFunctions.rush;
import org.vaadin.numerosity.Featureset.AppFunctions.zen;
import org.vaadin.numerosity.Subsystems.UserManager;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;

public class MainView extends VerticalLayout {

    private final UserManager userManager; // Dependency for user management

    public MainView(UserManager userManager) {
        this.userManager = userManager;

        // Header
        H1 header = new H1("Numerosity");
        header.getStyle().set("text-align", "center");
        add(header);

        // Navigation Sidebar
        VerticalLayout navigationBar = new VerticalLayout();
        navigationBar.getStyle().set("width", "250px");
        navigationBar.getStyle().set("border-right", "1px solid black");
        navigationBar.getStyle().set("height", "100vh");
        navigationBar.getStyle().set("padding", "20px");
        navigationBar.getStyle().set("position", "fixed");
        navigationBar.getStyle().set("display", "flex");
        navigationBar.getStyle().set("align-items", "center");

        // Login Form
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(e -> handleLogin(e.getUsername(), e.getPassword()));
        add(loginForm);

        // Signup Button
        Button signupButton = new Button("Sign Up", e -> openSignupDialog());
        add(signupButton);

        // Navigation Links
        RouterLink practiceLink = new RouterLink("Practice", bank.class);
        RouterLink rushLink = new RouterLink("Rush", rush.class);
        RouterLink dailyLink = new RouterLink("Daily", zen.class);
        navigationBar.add(practiceLink, rushLink, dailyLink);

        // Main Content Area
        Div contentArea = new Div();
        contentArea.getStyle().set("margin-left", "270px");
        contentArea.getStyle().set("padding", "20px");

        H2 welcomeMessage = new H2("Welcome to Numerosity!");
        welcomeMessage.getStyle().set("text-align", "center");
        contentArea.add(welcomeMessage);

        // Main Layout
        HorizontalLayout layout = new HorizontalLayout(navigationBar, contentArea);
        layout.setSizeFull();
        layout.setFlexGrow(1, contentArea);

        add(layout);
    }

    private void handleLogin(String username, String password) {
        try {
            boolean isAuthenticated = userManager.login(username, password);
            if (isAuthenticated) {
                Notification.show("Login successful!");
            } else {
                Notification.show("Invalid credentials!", 3000, Notification.Position.MIDDLE);
            }
        } catch (Exception e) {
            Notification.show("An error occurred during login: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            e.printStackTrace();
        }
    }

    private void openSignupDialog() {
        Dialog signupDialog = new Dialog();

        VerticalLayout signupLayout = new VerticalLayout();
        
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        TextField dobField = new TextField("Date of Birth (YYYY-MM-DD)");

        Button signupButton = new Button("Sign Up", e -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            String dob = dobField.getValue();

            try {
                String userId = userManager.signup(username, password, dob);
                Notification.show("Signup successful! Your User ID: " + userId);
                signupDialog.close();
            } catch (Exception ex) {
                Notification.show("Signup failed: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
                ex.printStackTrace();
            }
        });

        signupLayout.add(usernameField, passwordField, dobField, signupButton);
        
	add(signupLayout);
	signupDialog.add(signupLayout);
	signupDialog.open();
    }
}
