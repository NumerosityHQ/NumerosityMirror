package org.vaadin.numerosity;

import org.vaadin.numerosity.Featureset.AppFunctions.bank;
import org.vaadin.numerosity.Featureset.AppFunctions.rush;
import org.vaadin.numerosity.Featureset.AppFunctions.zen;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainView extends VerticalLayout {

    public MainView() {
        // Header
        H1 header = new H1("Numerosity");
        header.getStyle().set("text-align", "center");
        add(header);

        // Navigation Links
        Div navigationBar = new Div();
        navigationBar.getStyle().set("width", "200px");
        navigationBar.getStyle().set("border-right", "1px solid black");
        navigationBar.getStyle().set("height", "100%");
        navigationBar.getStyle().set("position", "fixed");

        RouterLink practiceLink = new RouterLink("Practice", bank.class);
        RouterLink rushLink = new RouterLink("Rush", rush.class);
        RouterLink dailyLink = new RouterLink("Daily", zen.class);

        navigationBar.add(practiceLink, rushLink, dailyLink);

        // Main Content Area
        Div contentArea = new Div();
        contentArea.getStyle().set("margin-left", "220px");
        contentArea.getStyle().set("padding", "20px");

        H2 welcomeMessage = new H2("Welcome to Numerosity!");
        welcomeMessage.getStyle().set("text-align", "center");
        contentArea.add(welcomeMessage);

        HorizontalLayout layout = new HorizontalLayout(navigationBar, contentArea);
        layout.setSizeFull();

        add(layout);
    }
}
