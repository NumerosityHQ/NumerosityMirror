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
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class for Firebase and application beans.
 */
@Configuration
@EnableWebMvc
public class ApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Value("${firebase.project.id}")
    private String projectId;

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Lazy
    public Firestore firestore() {
        if (FirebaseConfigurationSupport.isDemoMode(credentialsPath, projectId)) {
            log.info("Firebase configuration is empty. Running in demo mode; Firestore is disabled.");
            return null;
        }

        try (InputStream serviceAccount = FirebaseConfigurationSupport.openCredentialsStream(credentialsPath)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(projectId)
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.getApps().isEmpty()
                    ? FirebaseApp.initializeApp(options)
                    : FirebaseApp.getInstance();
            Firestore fs = FirestoreClient.getFirestore(firebaseApp);
            log.info("Firestore initialised successfully for project '{}'.", projectId);
            return fs;
        } catch (IOException e) {
            log.warn("Could not initialise Firestore – invalid or missing credentials in '{}'. "
                    + "Firestore features will be unavailable. Error: {}", credentialsPath, e.getMessage());
            return null;
        }
    }

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
