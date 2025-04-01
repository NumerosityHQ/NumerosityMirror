package org.vaadin.numerosity.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.vaadin.numerosity.Subsystems.UserManager;
import org.vaadin.numerosity.repository.FsUserRepository;
import org.vaadin.numerosity.repository.UserRepository;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@EnableWebMvc
public class ApplicationConfig {

    @Value("${firebase.project.id}")
    private String projectId;

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    @Bean
    public Firestore getFirestore() {
        try {

            InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(credentialsPath);
            GoogleCredentials credentials = GoogleCredentials.fromStream(inStream);
            FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                    .setProjectId(projectId)
                    .setCredentials(credentials)
                    .build();
            return firestoreOptions.getService();
        } catch (IOException e) {
            throw new RuntimeException("Exception while initializing Firestore", e);
        }
    }

    @Bean
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
    public Firestore firestore() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/Firebase.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://numerosity-583f5.firebaseio.com")
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore(firebaseApp);
    }

}
