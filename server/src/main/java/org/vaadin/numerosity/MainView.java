package org.vaadin.numerosity;

import org.vaadin.numerosity.Featureset.AppFunctions.bank;
import org.vaadin.numerosity.Featureset.AppFunctions.rush;
import org.vaadin.numerosity.Featureset.AppFunctions.zen;
import org.vaadin.numerosity.Subsystems.LoginHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.concurrent.ExecutionException;

/**
 * Modern MainView with dark/light mode toggle and responsive design.
 * Features a minimalist aesthetic with smooth transitions.
 */
@Route("")
@AnonymousAllowed
public class MainView extends VerticalLayout {

    @Autowired
    private LoginHandler loginHandler;

    private Button themeToggleButton;
    private boolean isDarkMode = false;

    public MainView() {
        initializeUI();
    }

    private void initializeUI() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        HorizontalLayout header = createHeader();
        add(header);

        Div contentArea = createContentArea();
        add(contentArea);
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle()
            .set("padding", "var(--lumo-space-m) var(--lumo-space-l)")
            .set("background-color", "var(--lumo-base-color)")
            .set("border-bottom", "1px solid var(--lumo-contrast-10pct)")
            .set("box-shadow", "var(--lumo-box-shadow-s)");

        H1 logo = new H1("Numerosity");
        logo.getStyle()
            .set("margin", "0")
            .set("font-size", "1.5rem")
            .set("font-weight", "600")
            .set("color", "var(--lumo-primary-text-color)");

        HorizontalLayout navLinks = new HorizontalLayout();
        navLinks.setSpacing(true);
        navLinks.setPadding(false);
        navLinks.setMargin(false);

        navLinks.add(createNavLink("Practice", bank.class),
            createNavLink("Rush", rush.class),
            createNavLink("Daily", zen.class));

        themeToggleButton = new Button();
        themeToggleButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        themeToggleButton.setIcon(new Icon(VaadinIcon.MOON));
        themeToggleButton.getElement().setAttribute("title", "Toggle dark mode");
        themeToggleButton.addClickListener(e -> toggleTheme());
        themeToggleButton.getStyle()
            .set("border-radius", "50%")
            .set("width", "40px")
            .set("height", "40px");

        Button loginButton = new Button("Login", e -> openLoginDialog());
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout rightSection = new HorizontalLayout();
        rightSection.setSpacing(true);
        rightSection.add(themeToggleButton, loginButton);

        header.add(logo, navLinks, rightSection);
        return header;
    }

    @SuppressWarnings("unchecked")
    private RouterLink createNavLink(String text, Class<? extends Component> navigationTarget) {
        RouterLink link = new RouterLink(text, navigationTarget);
        link.getStyle()
            .set("text-decoration", "none")
            .set("color", "var(--lumo-body-text-color)")
            .set("font-weight", "500")
            .set("padding", "var(--lumo-space-s) var(--lumo-space-m)")
            .set("border-radius", "var(--lumo-border-radius-m)")
            .set("transition", "background-color 0.2s ease");
        return link;
    }

    private Div createContentArea() {
        Div contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle()
            .set("display", "flex")
            .set("flex-direction", "column")
            .set("align-items", "center")
            .set("justify-content", "center")
            .set("padding", "var(--lumo-space-xl)");

        H2 welcomeMessage = new H2("Welcome to Numerosity!");
        welcomeMessage.getStyle()
            .set("text-align", "center")
            .set("color", "var(--lumo-primary-text-color)")
            .set("margin-bottom", "var(--lumo-space-l)");

        Span subtitle = new Span("Master mathematics through interactive practice and challenges");
        subtitle.getStyle()
            .set("color", "var(--lumo-secondary-text-color)")
            .set("font-size", "1.1rem")
            .set("text-align", "center")
            .set("max-width", "600px");

        contentArea.add(welcomeMessage, subtitle);
        return contentArea;
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;
        // Apply theme directly to UI element
        UI.getCurrent().getElement().setAttribute("theme", isDarkMode ? "dark" : "light");
        themeToggleButton.setIcon(new Icon(isDarkMode ? VaadinIcon.EYE : VaadinIcon.MOON));
    }

    private void openLoginDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Login / Sign Up");
        dialog.setWidth("400px");

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(true);

        TextField emailField = new TextField("Email");
        emailField.setPlaceholder("Enter your email");
        emailField.setWidthFull();

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("Enter your password");
        passwordField.setWidthFull();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        Button loginSubmitButton = new Button("Login", e -> handleLogin(emailField, passwordField, dialog));
        loginSubmitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button signupSubmitButton = new Button("Sign Up", e -> handleSignup(emailField, passwordField, dialog));

        buttonLayout.add(signupSubmitButton, loginSubmitButton);

        dialogLayout.add(emailField, passwordField, buttonLayout);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private void handleLogin(TextField emailField, PasswordField passwordField, Dialog dialog) {
        String email = emailField.getValue();
        String password = passwordField.getValue();
        
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            Notification.show("Please enter both email and password");
            return;
        }

        if (loginHandler == null) {
            Notification.show("Login service not available");
            return;
        }

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
    }

    private void handleSignup(TextField emailField, PasswordField passwordField, Dialog dialog) {
        String email = emailField.getValue();
        String password = passwordField.getValue();
        
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            Notification.show("Please enter both email and password");
            return;
        }

        if (loginHandler == null) {
            Notification.show("Signup service not available");
            return;
        }

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
    }
}