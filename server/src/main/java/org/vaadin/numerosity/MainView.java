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
        TextField nameField = new TextField("Your name");
        Button sayHelloButton = new Button("Say hello", event -> {
            try {
                String name = nameField.getValue(); // Get the value from the text field
                if (name == null || name.trim().isEmpty()) {
                    Notification.show("Please enter a name");
                    return;
                }
                String greeting = buttonInteraction.greet(name);
                Notification.show(greeting);
            } catch (Exception e) {
                Notification.show("An error occurred: " + e.getMessage());
                e.printStackTrace(); // Log the exception
            }
        });
        add(nameField, sayHelloButton);
    }
}
