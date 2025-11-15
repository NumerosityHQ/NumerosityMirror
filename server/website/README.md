![numerosity_org_cover](https://github.com/user-attachments/assets/a40c4845-7b31-4a02-ab4f-8676afe3ab30)

## Development
The current codebase is messy, half implemented, and **will** break. We strongly advise you to **not** use the application or implement it in your program yet. Please wait till the alpha release where we aim to have a basic implementation of features and documentation to go along with it.

## Codebase
This is also not the most up-to-date codebase. There does exist an internal codebase and parts of it are simply dumped into the repository. We are working under a tight timeframe and we hope to build and clean up once we get to the alpha release.

## Launch
This application is under active development and aims to launch in Q3 2025.
## Contributions

We do welcome contributions but we would advise you to please wait till the alpha version since the codebase is undergoing drastic changes.

## API Documentation

This section lists all public methods in the server codebase.

### org.vaadin.numerosity

#### Application
- `public static void main(String[] args)`
  **Definition:** Spring Boot application entry point that starts the server.
  **Usage Scenario:** Launches the Numerosity math practice application.

#### MainView
- `public MainView()`
  **Definition:** Constructor that builds the main UI with navigation sidebar, login dialog, and links to quiz modes.
  **Usage Scenario:** Displays the home page where users can log in and access different practice modes.

### org.vaadin.numerosity.config

#### ApplicationConfig
- `public Firestore getFirestore()`
  **Definition:** Creates Firestore instance using project ID and credentials from properties.
  **Usage Scenario:** Provides Firestore client for database operations.

- `public UserRepository userRepository(Firestore firestore)`
  **Definition:** Creates FsUserRepository bean with injected Firestore.
  **Usage Scenario:** Supplies user repository for dependency injection.

- `public PasswordEncoder passwordEncoder()`
  **Definition:** Creates BCryptPasswordEncoder bean.
  **Usage Scenario:** Provides password encoding for security.

- `public UserManager userManager(PasswordEncoder passwordEncoder)`
  **Definition:** Creates UserManager bean with password encoder.
  **Usage Scenario:** Supplies user management service.

- `public Firestore firestore() throws IOException`
  **Definition:** Initializes Firebase and returns Firestore client.
  **Usage Scenario:** Sets up Firebase connection for the application.

- `public MainView mainView()`
  **Definition:** Creates MainView bean with lazy initialization.
  **Usage Scenario:** Provides main UI view component.

### org.vaadin.numerosity.Featureset.AppFunctions

#### bank
- `public bank()`
  **Definition:** Constructor that initializes the Bank Mode UI, setting up selectors for subject, difficulty, question count, and start button.
  **Usage Scenario:** Creates the interface for users to configure and start a bank of questions for practice.

- `private void startSession()`
  **Definition:** Validates user selections and initiates the session based on chosen parameters.
  **Usage Scenario:** Called when start button is clicked; displays error if selections incomplete, otherwise confirms session start.

#### rush
- `public rush()`
  **Definition:** Constructor that sets up the Rush Mode UI with question display, answer buttons, and score tracking. Loads the first question.
  **Usage Scenario:** Initializes the timed quiz interface for fast-paced question answering.

- `private void loadQuestion()`
  **Definition:** Loads a random question and its answer choices from the database, updating the UI.
  **Usage Scenario:** Called to display a new question during the quiz session.

- `private void handleAnswer(int index) throws Exception`
  **Definition:** Processes the user's answer selection, checks correctness, updates score, shows notification, and loads the next question.
  **Usage Scenario:** Triggered by answer button clicks; handles scoring and progression in rush mode.

#### zen
- `public zen()`
  **Definition:** Constructor that initializes the Zen Mode UI with question display, answer buttons, and explanation toggle.
  **Usage Scenario:** Sets up a relaxed, non-scoring quiz environment for learning.

- `private void loadQuestion()`
  **Definition:** Loads a sample question into the display.
  **Usage Scenario:** Displays a question for the user to contemplate in zen mode.

- `private void handleAnswer()`
  **Definition:** Records the answer without scoring and shows a notification.
  **Usage Scenario:** Allows users to select answers in a stress-free manner.

- `private void toggleExplanation()`
  **Definition:** Toggles the visibility of the explanation section.
  **Usage Scenario:** Enables users to view or hide explanations for questions.

### org.vaadin.numerosity.Featureset.MathEngine

#### AlgebraOne
- `public static double solveLinear(double a, double b)`
  **Definition:** Solves the linear equation ax + b = 0 for x. Returns -b/a if a != 0, otherwise NaN.
  **Usage Scenario:** Used to find the root of a linear equation, e.g., for 2x - 4 = 0, call solveLinear(2, -4) returns 2.0.

- `public static double evaluateQuadratic(double a, double b, double c, double x)`
  **Definition:** Evaluates the quadratic expression a*x^2 + b*x + c at the given x value.
  **Usage Scenario:** To compute the y-value of a quadratic function at a point, e.g., for x^2 + 2x + 3 at x=4, call evaluateQuadratic(1, 2, 3, 4) returns 23.0.

- `public static double findSlope(double x1, double y1, double x2, double y2)`
  **Definition:** Calculates the slope (m) between two points (x1, y1) and (x2, y2) using (y2 - y1)/(x2 - x1).
  **Usage Scenario:** Determines the steepness of a line between two points, e.g., between (0,0) and (1,1), call findSlope(0, 0, 1, 1) returns 1.0.

#### AlgebraTwo
- `public static double[] solveQuadratic(double a, double b, double c)`
  **Definition:** Solves the quadratic equation ax^2 + bx + c = 0 and returns an array of real roots. Returns empty array if discriminant < 0.
  **Usage Scenario:** Finds roots of quadratic equations, e.g., for x^2 - 3x + 2 = 0, call solveQuadratic(1, -3, 2) returns [2.0, 1.0].

- `public static double evaluateExponential(double base, double exponent)`
  **Definition:** Computes base raised to the power of exponent.
  **Usage Scenario:** Calculates exponential values, e.g., 2^3, call evaluateExponential(2, 3) returns 8.0.

- `public static double logBaseN(double value, double base)`
  **Definition:** Computes the logarithm of value with the given base.
  **Usage Scenario:** Finds logarithms in different bases, e.g., log2(8), call logBaseN(8, 2) returns 3.0.

#### Calculus
- `public static double derivativeAtPoint(double a, double b, double c, double x)`
  **Definition:** Computes the derivative of the quadratic function ax^2 + bx + c at point x, which is 2ax + b.
  **Usage Scenario:** Finds the instantaneous rate of change, e.g., derivative of x^2 + 2x + 3 at x=4, call derivativeAtPoint(1, 2, 3, 4) returns 10.0.

- `public static double definiteIntegral(double a, double b, double c, double lower, double upper)`
  **Definition:** Calculates the definite integral of ax^2 + bx + c from lower to upper bounds.
  **Usage Scenario:** Computes area under a curve, e.g., integral of x^2 + 2x + 3 from 0 to 1, call definiteIntegral(1, 2, 3, 0, 1) returns the area value.

- `public static double limitApproaching(double a, double b, double c, double x)`
  **Definition:** Evaluates the quadratic function ax^2 + bx + c as x approaches the given value.
  **Usage Scenario:** Approximates function values near a point, e.g., limit of x^2 + 2x + 3 as x->5, call limitApproaching(1, 2, 3, 5) returns 38.0.

#### Geometry
- `public static double areaOfTriangle(double base, double height)`
  **Definition:** Calculates the area of a triangle using (1/2) * base * height.
  **Usage Scenario:** Finds triangle area, e.g., for base=4, height=5, call areaOfTriangle(4, 5) returns 10.0.

- `public static double circumferenceOfCircle(double radius)`
  **Definition:** Computes the circumference of a circle using 2 * π * radius.
  **Usage Scenario:** Calculates circle perimeter, e.g., for radius=3, call circumferenceOfCircle(3) returns approximately 18.84.

- `public static double distanceBetweenPoints(double x1, double y1, double x2, double y2)`
  **Definition:** Finds the Euclidean distance between two points.
  **Usage Scenario:** Measures straight-line distance, e.g., between (0,0) and (3,4), call distanceBetweenPoints(0, 0, 3, 4) returns 5.0.

#### Precalculus
- `public static double sine(double angle)`
  **Definition:** Computes the sine of an angle given in degrees.
  **Usage Scenario:** Trigonometric calculations, e.g., sin(30°), call sine(30) returns 0.5.

- `public static double cosine(double angle)`
  **Definition:** Computes the cosine of an angle given in degrees.
  **Usage Scenario:** Trigonometric calculations, e.g., cos(60°), call cosine(60) returns 0.5.

- `public static double tangent(double angle)`
  **Definition:** Computes the tangent of an angle given in degrees.
  **Usage Scenario:** Trigonometric calculations, e.g., tan(45°), call tangent(45) returns 1.0.

- `public static double factorial(int n)`
  **Definition:** Calculates the factorial of n using recursion (n!).
  **Usage Scenario:** Combinatorial calculations, e.g., 5!, call factorial(5) returns 120.0.

### org.vaadin.numerosity.Featureset.Supporter

#### OptionButton
- `public OptionButton()`
  **Definition:** Default constructor for OptionButton, extending Vaadin Button.
  **Usage Scenario:** Creates a button instance without initial text.

- `public OptionButton(String text)`
  **Definition:** Constructor that sets the button text.
  **Usage Scenario:** Creates a button with specified label text.

- `public String getData()`
  **Definition:** Retrieves the custom data associated with the button.
  **Usage Scenario:** Accesses stored data for the button, e.g., answer key or identifier.

- `public void setData(String data)`
  **Definition:** Sets custom data for the button.
  **Usage Scenario:** Associates additional information with the button, like option value.

### org.vaadin.numerosity.repository

#### FsUserRepository
- `public FsUserRepository(Firestore firestoneClient)`
  **Definition:** Constructor that initializes the repository with a Firestore instance.
  **Usage Scenario:** Creates a user repository for interacting with Firestore database.

- `public void createUserDocument(String userId, String username)`
  **Definition:** Creates a new user document in Firestore with initial statistics (correct, wrong, etc.).
  **Usage Scenario:** Called when registering a new user to set up their data record.

- `public void incrementCorrect(String userId)`
  **Definition:** Increments the correct answer count for the user.
  **Usage Scenario:** Updates user stats after a correct answer in quizzes.

- `public void incrementWrong(String userId)`
  **Definition:** Increments the wrong answer count for the user.
  **Usage Scenario:** Updates user stats after an incorrect answer.

- `private void updateStatistic(String userId, String field, int delta)`
  **Definition:** Helper method to increment a specific statistic field by delta.
  **Usage Scenario:** Internal use for updating counters like correct or wrong answers.

- `public Optional<Map<String, Object>> getUserStats(String userId)`
  **Definition:** Retrieves the user's statistics as a map, or empty if user doesn't exist.
  **Usage Scenario:** Fetches user performance data for display or analysis.

- `public boolean userExists(String userId)`
  **Definition:** Checks if a user document exists in Firestore.
  **Usage Scenario:** Validates user existence before operations.

#### UserRepository
- `void createUserDocument(String userId, String username)`
  **Definition:** Interface method to create user document.
  **Usage Scenario:** Defines contract for user creation in repository implementations.

- `void incrementCorrect(String userId)`
  **Definition:** Interface method to increment correct answers.
  **Usage Scenario:** Standardizes correct answer tracking across repositories.

- `void incrementWrong(String userId)`
  **Definition:** Interface method to increment wrong answers.
  **Usage Scenario:** Standardizes wrong answer tracking.

- `Optional<Map<String, Object>> getUserStats(String userId)`
  **Definition:** Interface method to get user stats.
  **Usage Scenario:** Defines retrieval of user statistics.

- `boolean userExists(String userId)`
  **Definition:** Interface method to check user existence.
  **Usage Scenario:** Defines user validation contract.

### org.vaadin.numerosity.rest

#### UserDTO
- `public String getUserId()`
  **Definition:** Getter for user ID.
  **Usage Scenario:** Retrieves the user identifier from the DTO.

- `public void setUserId(String userId)`
  **Definition:** Setter for user ID.
  **Usage Scenario:** Sets the user identifier in the DTO.

- `public String getUsername()`
  **Definition:** Getter for username.
  **Usage Scenario:** Retrieves the username from the DTO.

- `public void setUsername(String username)`
  **Definition:** Setter for username.
  **Usage Scenario:** Sets the username in the DTO.

#### UserRestController
- `public UserRestController(UserService userService)`
  **Definition:** Constructor injecting UserService.
  **Usage Scenario:** Initializes the REST controller with user service dependency.

- `public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO)`
  **Definition:** POST endpoint to create a user from DTO. Currently returns null (not implemented).
  **Usage Scenario:** Handles user creation requests via REST API.

### org.vaadin.numerosity.service

#### UserService
- `public UserService(UserRepository userRepository)`
  **Definition:** Constructor that injects UserRepository dependency.
  **Usage Scenario:** Initializes the service with repository for user operations.

- `public void createUser(String userId, String username)`
  **Definition:** Creates a new user by delegating to the repository.
  **Usage Scenario:** Handles user creation logic, calling repository to persist user data.

### org.vaadin.numerosity.Subsystems

#### BackgroundProcess
- `public void startSiteClock()`
  **Definition:** Starts timing the site session.
  **Usage Scenario:** Initiates clock when user begins using the application.

- `public void endSiteClock()`
  **Definition:** Stops the site session timer.
  **Usage Scenario:** Ends timing when user leaves the application.

- `public Duration getSiteClock()`
  **Definition:** Returns the duration of the site session.
  **Usage Scenario:** Retrieves total time spent on the site.

- `public void startQuestionClock()`
  **Definition:** Starts timing for a question.
  **Usage Scenario:** Begins timer when a question is presented.

- `public void endQuestionClock()`
  **Definition:** Stops the question timer.
  **Usage Scenario:** Ends timer when question is answered.

- `public Duration getQuestionClock()`
  **Definition:** Returns the time taken for the question.
  **Usage Scenario:** Gets duration for performance analysis.

#### DatabaseHandler
- `public DatabaseHandler(Firestore firestore)`
  **Definition:** Constructor injecting Firestore.
  **Usage Scenario:** Initializes database handler with Firestore client.

- `public void createUserDocument(String userId, String username) throws ExecutionException, InterruptedException`
  **Definition:** Creates user document in Firestore with initial stats.
  **Usage Scenario:** Sets up new user record.

- `public void updateStatistic(String userId, String field, int delta) throws ExecutionException, InterruptedException`
  **Definition:** Increments a user statistic field.
  **Usage Scenario:** Updates counters like correct/wrong answers.

- `public void incrementCorrect(String userId)`
  **Definition:** Increments correct answer count.
  **Usage Scenario:** Called after correct answer.

- `public void incrementWrong(String userId)`
  **Definition:** Increments wrong answer count.
  **Usage Scenario:** Called after incorrect answer.

- `public boolean userExists(String userId)`
  **Definition:** Checks if user exists in database.
  **Usage Scenario:** Validates user before operations.

- `public void saveQuestionData(String questionId, String userId, String answerId, boolean isCorrect)`
  **Definition:** Saves question attempt data.
  **Usage Scenario:** Records user answers for analysis.

- `public void deleteQuestionData(String questionId, String userId)`
  **Definition:** Removes question data.
  **Usage Scenario:** Cleans up or corrects data.

- `public void incrementCorrect()`
  **Definition:** Increments correct for current user.
  **Usage Scenario:** Updates stats without specifying userId.

- `public void incrementWrong()`
  **Definition:** Increments wrong for current user.
  **Usage Scenario:** Updates stats without specifying userId.

- `public String saveUserInfo(String userId, Map<String, Object> userInfo)`
  **Definition:** Saves user information map.
  **Usage Scenario:** Persists additional user data.

- `public String saveAnsweredProblem(String userId, String problemId, Map<String, Object> problemData)`
  **Definition:** Saves data for answered problem.
  **Usage Scenario:** Records problem-solving attempts.

- `public Map<String, Object> getUserInfo(String userId)`
  **Definition:** Retrieves user info.
  **Usage Scenario:** Fetches user details.

- `public Map<String, Object> getAnsweredProblem(String userId, String problemId)`
  **Definition:** Gets answered problem data.
  **Usage Scenario:** Retrieves specific problem attempts.

- `public void createUserAnswers(String userId) throws ExecutionException, InterruptedException`
  **Definition:** Initializes user answers document.
  **Usage Scenario:** Sets up answers storage for new user.

- `public void createUserInfo(String userId) throws ExecutionException, InterruptedException`
  **Definition:** Initializes user info document.
  **Usage Scenario:** Sets up info storage for new user.

- `public void setUserId(String userId)`
  **Definition:** Sets current user ID.
  **Usage Scenario:** Updates handler's user context.

- `public String getUsername()`
  **Definition:** Gets current username.
  **Usage Scenario:** Retrieves username for display.

- `public void setUsername(String username)`
  **Definition:** Sets current username.
  **Usage Scenario:** Updates handler's user context.

- `public String getUserId(String email)`
  **Definition:** Gets user ID from email via Firebase.
  **Usage Scenario:** Looks up user by email.

#### DataPlotter
- `public DataPlotter()`
  **Definition:** Constructor for data plotting service.
  **Usage Scenario:** Initializes plotter for data visualization.

- `public void plotData(String userId, String questionId, boolean correct, long timeTakenMillis, String difficulty, String subject)`
  **Definition:** Plots question attempt data.
  **Usage Scenario:** Records and visualizes user performance.

- `private Map<String, Object> createPlotData(...)`
  **Definition:** Creates data map for plotting.
  **Usage Scenario:** Prepares data for visualization.

- `private void savePlotData(Map<String, Object> plotData) throws IOException`
  **Definition:** Saves plot data to file.
  **Usage Scenario:** Persists visualization data.

#### FirebaseHandler
- `public void initialize() throws IOException`
  **Definition:** Initializes Firebase connection.
  **Usage Scenario:** Sets up Firebase for the application.

- `public void setPathToKey(String pathToKey)`
  **Definition:** Sets path to Firebase key file.
  **Usage Scenario:** Configures authentication key location.

- `public static void main(String[] args)`
  **Definition:** Main method for testing Firebase setup.
  **Usage Scenario:** Runs Firebase initialization test.

#### LocalDatabaseHandler
- `public String getRandomDirectory()`
  **Definition:** Selects random question directory.
  **Usage Scenario:** Chooses subject/level for random question.

- `public String getRandomQuestion()`
  **Definition:** Gets random question ID.
  **Usage Scenario:** Selects question for quiz.

- `public String getDirectory()`
  **Definition:** Gets current directory.
  **Usage Scenario:** Retrieves selected subject/level.

- `public void saveQuestion(String questionId, Map<String, Object> questionData) throws Exception`
  **Definition:** Saves question data locally.
  **Usage Scenario:** Stores new or updated questions.

- `public Map<String, Object> loadRandomQuestion() throws Exception`
  **Definition:** Loads random question data.
  **Usage Scenario:** Fetches question for display.

- `public Map<String, Object> getChosenQuestionMap() throws Exception`
  **Definition:** Gets data for chosen question.
  **Usage Scenario:** Retrieves specific question details.

- `public String getChosenQuestion() throws Exception`
  **Definition:** Gets chosen question content.
  **Usage Scenario:** Displays selected question.

- `public Map<String, Object> loadSpecificQuestion(String questionId, String filePath) throws Exception`
  **Definition:** Loads specific question by ID and path.
  **Usage Scenario:** Fetches particular question data.

- `private Map<String, Object> convertJsonObjectToMap(JsonObject jsonObject)`
  **Definition:** Converts JSON to map.
  **Usage Scenario:** Parses question data from JSON.

- `public Map<String, Object> loadRandomQuestionByLevel(String directory) throws Exception`
  **Definition:** Loads random question from specific level.
  **Usage Scenario:** Gets question by difficulty/subject.

- `public String getAnswerChoiceText(String questionId, String optionId) throws Exception`
  **Definition:** Gets text for answer choice.
  **Usage Scenario:** Displays option text.

- `public void setDirectory(String directory)`
  **Definition:** Sets current directory.
  **Usage Scenario:** Changes subject/level selection.

- `public String getSpecificDirectory()`
  **Definition:** Gets specific directory.
  **Usage Scenario:** Retrieves directory path.

- `public void markQuestionAsAttempted(String questionId)`
  **Definition:** Marks question as attempted.
  **Usage Scenario:** Tracks question status.

- `public void markQuestionAsUnattempted(String questionId)`
  **Definition:** Marks question as unattempted.
  **Usage Scenario:** Resets question status.

- `public void markQuestionAsCorrect(String questionId)`
  **Definition:** Marks question as correctly answered.
  **Usage Scenario:** Updates question completion status.

#### LoginHandler
- `public String signup(String email, String password) throws ExecutionException, InterruptedException`
  **Definition:** Registers new user with Firebase.
  **Usage Scenario:** Creates new user account.

- `public String login(String email, String password) throws ExecutionException, InterruptedException`
  **Definition:** Authenticates user with Firebase.
  **Usage Scenario:** Logs in existing user.

- `public boolean logout(String idToken)`
  **Definition:** Logs out user by revoking token.
  **Usage Scenario:** Ends user session.

#### QuestionContentLoader
- `public QuestionContentLoader(LocalDatabaseHandler localDbHandler, Application application)`
  **Definition:** Constructor injecting dependencies.
  **Usage Scenario:** Initializes loader with database and app context.

- `public String loadRandomAsText() throws Exception`
  **Definition:** Loads random question as plain text.
  **Usage Scenario:** Displays question without formatting.

- `public String loadRandomAsLatex() throws Exception`
  **Definition:** Loads random question as LaTeX.
  **Usage Scenario:** Displays formatted math questions.

- `public String loadAsText(String questionId, String databasePath) throws Exception`
  **Definition:** Loads specific question as text.
  **Usage Scenario:** Retrieves plain text for given question.

- `public String loadAsLatex(String questionId, String databasePath) throws Exception`
  **Definition:** Loads specific question as LaTeX.
  **Usage Scenario:** Retrieves formatted question.

- `public String getAnswerChoice(String letter) throws Exception`
  **Definition:** Gets answer choice text by letter.
  **Usage Scenario:** Displays option for given letter.

- `public String getCorrectAnswerKey() throws Exception`
  **Definition:** Gets key of correct answer.
  **Usage Scenario:** Checks user answer correctness.

- `public String getCurrentQuestionId() throws Exception`
  **Definition:** Gets ID of current question.
  **Usage Scenario:** Identifies loaded question.

#### UserManager
- `private void initializeDirectories()`
  **Definition:** Sets up user data directories.
  **Usage Scenario:** Prepares file system for user storage.

- `public String signup(String username, String password, String dob) throws IOException`
  **Definition:** Registers new user locally.
  **Usage Scenario:** Creates local user account.

- `public boolean login(String username, String password) throws IOException`
  **Definition:** Authenticates user locally.
  **Usage Scenario:** Logs in with local credentials.

- `public void deleteAccount(String userId) throws IOException`
  **Definition:** Deletes user account and data.
  **Usage Scenario:** Removes user from system.

- `public String getUserId(String username) throws IOException`
  **Definition:** Gets user ID by username.
  **Usage Scenario:** Looks up user identifier.

- `public String getUserDob(String username) throws IOException`
  **Definition:** Gets user's date of birth.
  **Usage Scenario:** Retrieves user profile info.

- `public String getUsername(String userId) throws IOException`
  **Definition:** Gets username by user ID.
  **Usage Scenario:** Retrieves username for display.

- `private UserInfo getUserInfo(String username) throws IOException`
  **Definition:** Loads user info from file.
  **Usage Scenario:** Fetches user details.

- `private boolean isUsernameTaken(String username) throws IOException`
  **Definition:** Checks if username exists.
  **Usage Scenario:** Validates unique usernames.

- `private Path findUserDirectory(String username) throws IOException`
  **Definition:** Finds user's directory path.
  **Usage Scenario:** Locates user data storage.

- `private <T> T readJson(Path path, Class<T> type) throws IOException`
  **Definition:** Reads JSON file into object.
  **Usage Scenario:** Deserializes user data.

- `private void writeJson(Path path, Object value) throws IOException`
  **Definition:** Writes object to JSON file.
  **Usage Scenario:** Serializes user data.

- `private void deleteSilently(Path path)`
  **Definition:** Deletes file quietly.
  **Usage Scenario:** Cleans up user files.

- `public void saveUserAnswers(Map<String, Object> userAnswers)`
  **Definition:** Saves user's answer data.
  **Usage Scenario:** Persists quiz results.



