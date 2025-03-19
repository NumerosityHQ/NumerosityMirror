// MainView.java
package org.vaadin.numerosity;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    private ButtonInteraction buttonInteraction;

    public MainView() {
        TextField nameField = new TextField("Text");
        // Existing button
        Button sayHelloButton = new Button("Click:", event -> {
            try {
                String name = nameField.getValue();
                if (name == null || name.trim().isEmpty()) {
                    Notification.show("Please enter text");
                    return;
                }
                String greeting = buttonInteraction.greet(name);
                Notification.show(greeting);
            } catch (Exception e) {
                Notification.show("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // New button calling another method
        Button incrementAction = new Button("Increment score", event -> {
            try {
                String response = "Score incremented";
                buttonInteraction.incrementAction();
                Notification.show(response);
            } catch (Exception e) {
                Notification.show("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // New button calling another method
        Button decrementAction = new Button("Decrement score", event -> {
            try {
                String response = "Score decremented";
                buttonInteraction.decrementAction();
                Notification.show(response);
            } catch (Exception e) {
                Notification.show("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        });
        add(nameField, sayHelloButton, decrementAction, incrementAction);
    }
}
