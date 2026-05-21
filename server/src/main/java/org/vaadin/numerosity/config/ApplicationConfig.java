package org.vaadin.numerosity.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.vaadin.numerosity.MainView;
import org.vaadin.numerosity.Subsystems.UserManager;
import org.vaadin.numerosity.repository.FsUserRepository;
import org.vaadin.numerosity.repository.UserRepository;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebMvc
public class ApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Value("${firebase.project.id}")
    private String projectId;

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    /**
     * Required for {@code @Value} injection to work when {@link EnableWebMvc}
     * is present (it disables the Boot auto-configured placeholder configurer).
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Builds a Firestore instance from the service-account JSON on the classpath.
     * <p>
     * If the credentials file is missing or contains invalid data the exception is
     * caught and {@code null} is returned so that the rest of the application can
     * still start. Features that need Firestore will fail at the point of use,
     * not at application boot.
     */
    @Bean
    @Lazy
    public Firestore firestore() {
        // Guard against @Value not resolving (e.g. missing property)
        if (credentialsPath == null || credentialsPath.isBlank()) {
            log.warn("Property 'firebase.credentials.path' is not set. "
                    + "Firestore features will be unavailable.");
            return null;
        }

        try {
            InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream(credentialsPath);
            if (serviceAccount == null) {
                log.warn("Firebase credentials file '{}' not found on classpath. "
                        + "Firestore features will be unavailable until a valid service-account "
                        + "JSON is placed at src/main/resources/{}.",
                        credentialsPath, credentialsPath);
                return null;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(projectId)
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
            Firestore fs = FirestoreClient.getFirestore(firebaseApp);
            log.info("Firestore initialised successfully for project '{}'.", projectId);
            return fs;
        } catch (IOException e) {
            log.warn("Could not initialise Firestore – invalid or missing credentials in '{}'. "
                    + "Firestore features will be unavailable. Error: {}", credentialsPath, e.getMessage());
            return null;
        }
    }

    /**
     * Only register the Firestore-backed repository when a valid Firestore instance
     * is available. When {@code firestore} is {@code null} (bad / missing credentials)
     * this bean is simply omitted from the context.
     */
    @Bean
    @Lazy
    @Conditional(FirestoreAvailableCondition.class)
    public UserRepository userRepository(Firestore firestore) {
        return new FsUserRepository(firestore);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserManager userManager(PasswordEncoder passwordEncoder) {
        return new UserManager(passwordEncoder);
    }

    @Bean
    @Lazy
    public MainView mainView() {
        return new MainView();
    }
}
