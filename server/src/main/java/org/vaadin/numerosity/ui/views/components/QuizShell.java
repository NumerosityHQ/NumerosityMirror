package org.vaadin.numerosity.ui.views.components;

import org.vaadin.numerosity.Featureset.AppFunctions.bank;
import org.vaadin.numerosity.Featureset.AppFunctions.rush;
import org.vaadin.numerosity.Featureset.AppFunctions.zen;
import org.vaadin.numerosity.ui.views.DashboardView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

/**
 * Shared shell used by quiz modes to match the homepage and dashboard styling.
 */
public final class QuizShell {

    private QuizShell() {
    }

    public static VerticalLayout createPage(String title, String subtitle) {
        VerticalLayout page = new VerticalLayout();
        page.setSizeFull();
        page.setPadding(false);
        page.setSpacing(false);
        page.setMargin(false);
        page.getStyle()
                .set("background", "var(--lumo-base-color)")
                .set("min-height", "100vh");

        page.add(createHeader(), createHero(title, subtitle));
        return page;
    }

    public static Div createContentShell() {
        Div shell = new Div();
        shell.setWidthFull();
        shell.getStyle()
                .set("padding", "0 var(--lumo-space-l) var(--lumo-space-l)")
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("gap", "var(--lumo-space-l)");
        return shell;
    }

    public static Div createPanel() {
        Div panel = new Div();
        panel.getStyle()
                .set("padding", "var(--lumo-space-l)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background", "var(--lumo-contrast-5pct)")
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("box-shadow", "var(--lumo-box-shadow-xs)");
        return panel;
    }

    public static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return button;
    }

    public static Button createSecondaryButton(String text) {
        Button button = new Button(text);
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return button;
    }

    public static Span createPill(String text, VaadinIcon icon) {
        Span pill = new Span();
        pill.getStyle()
                .set("display", "inline-flex")
                .set("align-items", "center")
                .set("gap", "0.35rem")
                .set("padding", "0.35rem 0.7rem")
                .set("border-radius", "999px")
                .set("background", "var(--lumo-base-color)")
                .set("border", "1px solid var(--lumo-contrast-10pct)")
                .set("color", "var(--lumo-secondary-text-color)")
                .set("font-size", "0.85rem");
        pill.add(new Icon(icon), new Span(text));
        return pill;
    }

    private static HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.getStyle()
                .set("padding", "var(--lumo-space-m) var(--lumo-space-l)")
                .set("background-color", "var(--lumo-base-color)")
                .set("border-bottom", "1px solid var(--lumo-contrast-10pct)")
                .set("box-shadow", "var(--lumo-box-shadow-s)");

        H1 logo = new H1("Numerosity");
        logo.getStyle()
                .set("margin", "0")
                .set("font-size", "1.5rem")
                .set("font-weight", "600")
                .set("color", "var(--lumo-primary-text-color)");

        HorizontalLayout navLinks = new HorizontalLayout();
        navLinks.setSpacing(true);
        navLinks.setPadding(false);
        navLinks.setMargin(false);
        navLinks.add(createNavLink("Practice", bank.class),
                createNavLink("Rush", rush.class),
                createNavLink("Daily", zen.class),
                createNavLink("Dashboard", DashboardView.class));

        HorizontalLayout rightSection = new HorizontalLayout();
        rightSection.setSpacing(true);
        rightSection.add(
                createPill("Quiz mode", VaadinIcon.BOOK),
                createPill("Consistent UI", VaadinIcon.PALETTE));

        header.add(logo, navLinks, rightSection);
        return header;
    }

    @SuppressWarnings("unchecked")
    private static RouterLink createNavLink(String text, Class<? extends Component> navigationTarget) {
        RouterLink link = new RouterLink(text, navigationTarget);
        link.getStyle()
                .set("text-decoration", "none")
                .set("color", "var(--lumo-body-text-color)")
                .set("font-weight", "500")
                .set("padding", "var(--lumo-space-s) var(--lumo-space-m)")
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("transition", "background-color 0.2s ease");
        return link;
    }

    private static Div createHero(String title, String subtitle) {
        Div hero = new Div();
        hero.getStyle()
                .set("padding", "var(--lumo-space-xl)")
                .set("margin", "var(--lumo-space-l)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("background", "linear-gradient(135deg, var(--lumo-primary-color-10pct), var(--lumo-contrast-5pct))")
                .set("border", "1px solid var(--lumo-contrast-10pct)");

        H2 heading = new H2(title);
        heading.getStyle()
                .set("margin", "0 0 var(--lumo-space-s) 0")
                .set("font-size", "2rem");

        Paragraph body = new Paragraph(subtitle);
        body.getStyle()
                .set("margin", "0")
                .set("max-width", "62ch")
                .set("color", "var(--lumo-secondary-text-color)");

        hero.add(heading, body);
        return hero;
    }
}
