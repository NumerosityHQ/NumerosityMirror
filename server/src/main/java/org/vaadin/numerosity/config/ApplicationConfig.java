package org.vaadin.numerosity.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.vaadin.numerosity.repository.FsUserRepository;
import org.vaadin.numerosity.repository.UserRepository;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

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

}
