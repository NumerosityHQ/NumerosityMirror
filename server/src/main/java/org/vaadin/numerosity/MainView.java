package org.vaadin.numerosity;

import com.vaadin.flow.component.button.Button; import com.vaadin.flow.component.html.Div; import com.vaadin.flow.component.html.H1; import com.vaadin.flow.component.html.H2; import com.vaadin.flow.component.notification.Notification; import com.vaadin.flow.component.orderedlayout.HorizontalLayout; import com.vaadin.flow.component.orderedlayout.VerticalLayout; import com.vaadin.flow.component.textfield.PasswordField; import com.vaadin.flow.component.textfield.TextField; import com.vaadin.flow.router.BeforeEvent; import com.vaadin.flow.router.HasUrlParameter; import com.vaadin.flow.router.OptionalParameter; import com.vaadin.flow.router.Route; import com.vaadin.flow.router.RouterLink; import org.springframework.beans.factory.annotation.Autowired; import org.vaadin.numerosity.Featureset.AppFunctions.bank; import org.vaadin.numerosity.Featureset.AppFunctions.rush; import org.vaadin.numerosity.Featureset.AppFunctions.zen; import org.vaadin.numerosity.Subsystems.DataPlotter; import org.vaadin.numerosity.Subsystems.DatabaseHandler; import org.vaadin.numerosity.Subsystems.FirebaseHandler; import org.vaadin.numerosity.Subsystems.LocalDatabaseHandler; import org.vaadin.numerosity.Subsystems.QuestionContentLoader; import org.vaadin.numerosity.Subsystems.ResponseHandler; import org.vaadin.numerosity.entity.User; import org.vaadin.numerosity.repository.UserRepository;

import java.util.List; import java.util.Map;

@Route("main") public class MainView extends VerticalLayout implements HasUrlParameter<String> {

private String userId;
private Div contentArea = new Div();
private H2 welcomeMessage = new H2();
private final FirebaseHandler firebaseService;
private final QuestionContentLoader questionLoader;
private final LocalDatabaseHandler localDbHandler;
private final DatabaseHandler DbHandler;
private final DataPlotter dataPlotter;
private Map<String, Object> questionJson;
private ResponseHandler responseTracker;

// Login/Registration Components
private TextField usernameField = new TextField("Username");
private PasswordField passwordField = new PasswordField("Password");
private Button loginButton = new Button("Login");
private Button registerButton = new Button("Register");

@Autowired
private UserRepository userRepository;

@Autowired
public MainView(FirebaseHandler firebaseService, QuestionContentLoader questionLoader, UserRepository userRepository) {
    this.firebaseService = firebaseService;
    this.questionLoader = questionLoader;
    this.localDbHandler = null;
    this.DbHandler = null;
    this.dataPlotter = new DataPlotter();
    this.userRepository = userRepository;

    // Header
    H1 header = new H1("Numerosity");
    header.getStyle().set("text-align", "center");
    add(header);

    // Create and add the login page layout
    VerticalLayout loginPage = createLoginForm();
    add(loginPage);

    // Create navigation bar (initially hidden)
    Div navigationBar = createNavigationBar();
    navigationBar.setVisible(false);

    // Configure the content area that will display questions after login
    contentArea.getStyle().set("margin-left", "220px");
    contentArea.getStyle().set("padding", "20px");
    contentArea.add(welcomeMessage);
    contentArea.setVisible(false);

    HorizontalLayout mainLayout = new HorizontalLayout(navigationBar, contentArea);
    mainLayout.setSizeFull();
    add(mainLayout);
}

/**
 * Creates a login form that includes login and registration functionality.
 */
private VerticalLayout createLoginForm() {
    VerticalLayout loginForm = new VerticalLayout();

    // Configure Login Button
    loginButton.addClickListener(e -> {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Authentication successful – update state and show main content
            this.userId = username;
            welcomeMessage.setText("Welcome to Numerosity, " + userId + "!");
            loadQuestionsAndInitialize();
            showMainView();
        } else {
            Notification.show("Invalid username or password.");
        }
    });

    // Configure Registration Button
    registerButton.addClickListener(e -> {
        String username = usernameField.getValue();
        String password = passwordField.getValue();
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            Notification.show("Username and password cannot be empty.");
            return;
        }
        if (userRepository.findByUsername(username) != null) {
            Notification.show("Username already exists.");
            return;
        }
        User newUser = new User(username, password);
        userRepository.save(newUser);
        Notification.show("Registration successful. Please log in.");
    });

    loginForm.add(usernameField, passwordField, loginButton, registerButton);
    return loginForm;
}

/**
 * Shows the main view (navigation bar and content area) after successful login.
 */
private void showMainView() {
    // Find the navigation bar and content area from the layout
    HorizontalLayout layout = (HorizontalLayout) this.getChildren()
            .filter(component -> component instanceof HorizontalLayout)
            .findFirst().orElse(null);

    if (layout != null) {
        Div navigationBar = (Div) layout.getChildren()
                .filter(component -> component instanceof Div)
                .findFirst().orElse(null);

        if (navigationBar != null) {
            navigationBar.setVisible(true);
        }
        contentArea.setVisible(true);

        // Hide the login form
        getChildren().filter(component -> component instanceof VerticalLayout)
                .findFirst().ifPresent(component -> component.setVisible(false));
    }
}

/**
 * Creates a navigation bar with links to other views.
 */
private Div createNavigationBar() {
    Div navigationBar = new Div();
    navigationBar.getStyle().set("width", "200px");
    navigationBar.getStyle().set("border-right", "1px solid black");
    navigationBar.getStyle().set("height", "100%");
    navigationBar.getStyle().set("position", "fixed");

    RouterLink practiceLink = new RouterLink("Practice", bank.class);
    RouterLink rushLink = new RouterLink("Rush", rush.class);
    RouterLink dailyLink = new RouterLink("Daily", zen.class);

    navigationBar.add(practiceLink, rushLink, dailyLink);
    return navigationBar;
}

@Override
public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
    this.userId = parameter;
    if (userId != null && !userId.isEmpty()) {
        welcomeMessage.setText("Welcome to Numerosity, " + userId + "!");
        loadQuestionsAndInitialize();
    } else {
        welcomeMessage.setText("Please log in.");
    }
}

/**
 * Loads questions from the question loader and initializes the question display.
 */
private void loadQuestionsAndInitialize() {
    try {
        questionJson = questionLoader.loadAsMap();
        displayQuestion();
    } catch (Exception e) {
        Notification.show("Failed to load questions: " + e.getMessage());
        e.printStackTrace();
    }
}

/**
 * Displays the first question from the loaded JSON.
 */
private void displayQuestion() {
    List<Map<String, Object>> questions = (List<Map<String, Object>>) questionJson.get("questions");
    if (questions != null && !questions.isEmpty()) {
        Map<String, Object> firstQuestion = questions.get(0);
        contentArea.add(new H2((String) firstQuestion.get("text")));

        Button answerButton = new Button("Answer", e -> {
            boolean isCorrect = true; // Replace with actual answer checking logic
            String questionId = (String) firstQuestion.get("id");
            String subject = firstQuestion.get("tags").toString();
            String difficulty = (String) firstQuestion.get("difficulty");

            responseTracker.logResponse(questionId, isCorrect, subject, difficulty);
            firebaseService.saveResponse(userId, responseTracker.getResponseLog());

            updateQuestionJson(questionJson, questionId, "userAnswer", 10);
            Notification.show("Answered and saved!");
            displayQuestion();
        });
        contentArea.add(answerButton);
    } else {
        contentArea.add(new H2("No questions available."));
    }
}

/**
 * Updates the question JSON with the user’s answer and time taken.
 */
public void updateQuestionJson(Map<String, Object> questionJson, String questionId, String userAnswer, long timeTaken) {
    List<Map<String, Object>> questions = (List<Map<String, Object>>) questionJson.get("questions");
    for (Map<String, Object> question : questions) {
        if (question.get("id").equals(questionId)) {
            question.put("user_answer", userAnswer);
            question.put("user_time_taken", timeTaken);
            break;
        }
    }
}

}