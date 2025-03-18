// MainView.java
package org.vaadin.numerosity;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.numerosity.Subsystems.QuestionContentLoader;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    private ButtonInteraction buttonInteraction;

    @Autowired
    private QuestionContentLoader questionContentLoader;

    // return questionContentLoader.loadAsText();

    public MainView() {
        TextField nameField = new TextField("Event Handler Test");
        // add buttons to the layout, if pressed call method return
        // questionContentLoader.loadAsText();
        Button testButton = new Button("Test", event -> {
            try {
                String question = questionContentLoader.loadAsText();
                Notification.show(question);
            } catch (Exception e) {
                Notification.show("An error occurred: " + e.getMessage());
                e.printStackTrace(); // Log the exception
            }
        });
        Button sayHelloButton = new Button("Click", event -> {
            try {
                String name = nameField.getValue(); // Get the value from the text field
                if (name == null || name.trim().isEmpty()) {
                    Notification.show("Please enter text");
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
