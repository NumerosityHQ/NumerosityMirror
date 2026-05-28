package org.vaadin.numerosity.ui.views;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.ObjectProvider;
import org.vaadin.numerosity.repository.UserRepository;
import org.vaadin.numerosity.ui.ThemeManager;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("dashboard")
@AnonymousAllowed
public class DashboardView extends VerticalLayout {

    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("0.0");

    private final ObjectProvider<UserRepository> userRepositoryProvider;
    private final ThemeManager themeManager;
    private final Map<String, Span> metricValues = new LinkedHashMap<>();

    private Span connectionBadge;
    private Span lastUpdatedText;
    private Button themeToggleButton;

    public DashboardView(ObjectProvider<UserRepository> userRepositoryProvider, ThemeManager themeManager) {
        this.userRepositoryProvider = userRepositoryProvider;
        this.themeManager = themeManager;
        initializeUI();
    }

    private void initializeUI() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setMargin(false);
        getStyle().set("background", "var(--lumo-base-color)");

        Div shell = new Div();
        shell.setWidthFull();
        shell.getStyle()
            .set("min-height", "100vh")
            .set("padding", "var(--lumo-space-l)")
            .set("display", "flex")
            .set("flex-direction", "column")
            .set("gap", "var(--lumo-space-l)");

        shell.add(createHeader(), createHero(), createMetricsGrid(), createInsightsGrid(), createActivityPanel());
        add(shell);
        refreshDashboardData();
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setPadding(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.getStyle()
            .set("border-radius", "var(--lumo-border-radius-l)")
            .set("background", "var(--lumo-contrast-5pct)")
            .set("border", "1px solid var(--lumo-contrast-10pct)");

        Div brand = new Div();
        H1 title = new H1("Numerosity Dashboard");
        title.getStyle().set("margin", "0").set("font-size", "1.55rem").set("font-weight", "700");
        Span subtitle = new Span("Performance, streaks, and learning momentum");
        subtitle.getStyle().set("color", "var(--lumo-secondary-text-color)");
        brand.add(title, subtitle);

        HorizontalLayout actions = new HorizontalLayout();
        actions.setAlignItems(FlexComponent.Alignment.CENTER);
        actions.setSpacing(true);
        connectionBadge = createPill("Demo data", VaadinIcon.INFO_CIRCLE, "var(--lumo-tertiary-text-color)");
        lastUpdatedText = new Span("Last updated just now");
        lastUpdatedText.getStyle().set("color", "var(--lumo-secondary-text-color)").set("font-size", "0.9rem");
        themeToggleButton = new Button(new Icon(VaadinIcon.MOON));
        themeToggleButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        themeToggleButton.addClickListener(e -> toggleTheme());
        actions.add(connectionBadge, lastUpdatedText, themeToggleButton);

        header.add(brand, actions);
        return header;
    }

    private Div createHero() {
        Div hero = new Div();
        hero.getStyle()
            .set("padding", "var(--lumo-space-xl)")
            .set("border-radius", "var(--lumo-border-radius-l)")
            .set("background", "linear-gradient(135deg, var(--lumo-primary-color-10pct), var(--lumo-contrast-5pct))")
            .set("border", "1px solid var(--lumo-contrast-10pct)");

        H2 heading = new H2("Your learning snapshot");
        heading.getStyle().set("margin", "0 0 var(--lumo-space-s) 0").set("font-size", "2rem");
        Paragraph body = new Paragraph(
            "See how many questions you are answering correctly, where time is being spent, and how your streak is trending.");
        body.getStyle().set("margin", "0").set("max-width", "62ch").set("color", "var(--lumo-secondary-text-color)");

        HorizontalLayout quickStats = new HorizontalLayout();
        quickStats.setWrap(true);
        quickStats.setSpacing(true);
        quickStats.getStyle().set("margin-top", "var(--lumo-space-l)");
        quickStats.add(
            createMiniStat("Accuracy", "0%"),
            createMiniStat("Current streak", "0 days"),
            createMiniStat("Avg. time", "0.0s"),
            createMiniStat("Questions answered", "0"));

        hero.add(heading, body, quickStats);
        return hero;
    }

    private Div createMetricsGrid() {
        Div grid = new Div();
        grid.getStyle()
            .set("display", "grid")
            .set("grid-template-columns", "repeat(auto-fit, minmax(220px, 1fr))")
            .set("gap", "var(--lumo-space-l)");

        grid.add(
            createMetricCard("Total Correct", "0", VaadinIcon.CHECK, "var(--lumo-success-color)", "Solid answers completed correctly."),
            createMetricCard("Total Incorrect", "0", VaadinIcon.CLOSE_CIRCLE, "var(--lumo-error-color)", "Answers missed or corrected later."),
            createMetricCard("Accuracy %", "0%", VaadinIcon.BAR_CHART, "var(--lumo-primary-color)", "Share of questions answered correctly."),
            createMetricCard("Average Time / Question", "0.0s", VaadinIcon.CLOCK, "var(--lumo-contrast-80pct)", "Speed across all answered questions."),
            createMetricCard("Current Streak", "0 days", VaadinIcon.FIRE, "var(--lumo-warning-color)", "Consecutive active days."),
            createMetricCard("Total Answered", "0", VaadinIcon.BOOK, "var(--lumo-contrast-80pct)", "Total questions completed."));
        return grid;
    }

    private Div createInsightsGrid() {
        Div grid = new Div();
        grid.getStyle()
            .set("display", "grid")
            .set("grid-template-columns", "repeat(auto-fit, minmax(320px, 1fr))")
            .set("gap", "var(--lumo-space-l)")
            .set("margin-top", "var(--lumo-space-l)");
        grid.add(createInsightPanel(), createProgressPanel());
        return grid;
    }

    private Div createActivityPanel() {
        Div panel = createPanel();
        panel.add(createPanelHeader("Recent activity", VaadinIcon.LIST_OL));
        panel.add(createActivityItem("Today", "8 correct, 2 incorrect", "Current session performance"));
        panel.add(createActivityItem("Yesterday", "14 questions answered", "Best streak so far this week"));
        panel.add(createActivityItem("This week", "Average time improved by 1.2s", "Measured across all attempts"));
        return panel;
    }

    private Div createInsightPanel() {
        Div panel = createPanel();
        panel.add(createPanelHeader("Insights", VaadinIcon.CHART));
        panel.add(createInsightRow("Strongest area", "Accuracy is holding steady", "Your correct answers are outpacing misses."));
        panel.add(createInsightRow("Focus area", "Reduce response time", "Work on answering a little faster without sacrificing accuracy."));
        panel.add(createInsightRow("Momentum", "Keep the streak alive", "A small daily session will preserve consistency."));
        return panel;
    }

    private Div createProgressPanel() {
        Div panel = createPanel();
        panel.add(createPanelHeader("Progress mix", VaadinIcon.LINE_CHART));
        panel.add(createProgressBar("Correct", "72%", "var(--lumo-success-color)", 72));
        panel.add(createProgressBar("Incorrect", "18%", "var(--lumo-error-color)", 18));
        panel.add(createProgressBar("Unattempted", "10%", "var(--lumo-contrast-30pct)", 10));
        return panel;
    }

    private Div createPanel() {
        Div panel = new Div();
        panel.getStyle()
            .set("padding", "var(--lumo-space-l)")
            .set("border-radius", "var(--lumo-border-radius-l)")
            .set("background", "var(--lumo-contrast-5pct)")
            .set("border", "1px solid var(--lumo-contrast-10pct)");
        return panel;
    }

    private HorizontalLayout createPanelHeader(String label, VaadinIcon icon) {
        HorizontalLayout header = new HorizontalLayout();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        Icon i = new Icon(icon);
        i.getStyle().set("color", "var(--lumo-primary-color)");
        H3 heading = new H3(label);
        heading.getStyle().set("margin", "0").set("font-size", "1.1rem");
        header.add(i, heading);
        return header;
    }

    private Div createMetricCard(String label, String value, VaadinIcon icon, String accentColor, String hint) {
        Div card = createPanel();
        Icon metricIcon = new Icon(icon);
        metricIcon.getStyle().set("color", accentColor).set("font-size", "1.5rem");
        Span valueText = new Span(value);
        valueText.getStyle().set("display", "block").set("font-size", "2rem").set("font-weight", "700").set("margin", "var(--lumo-space-s) 0");
        Span labelText = new Span(label);
        labelText.getStyle().set("display", "block").set("font-weight", "600").set("margin-bottom", "var(--lumo-space-xs)");
        Span hintText = new Span(hint);
        hintText.getStyle().set("color", "var(--lumo-secondary-text-color)").set("font-size", "0.9rem");
        card.add(metricIcon, valueText, labelText, hintText);
        metricValues.put(label, valueText);
        return card;
    }

    private HorizontalLayout createMiniStat(String label, String value) {
        HorizontalLayout item = new HorizontalLayout();
        item.setAlignItems(FlexComponent.Alignment.CENTER);
        item.getStyle()
            .set("padding", "var(--lumo-space-s) var(--lumo-space-m)")
            .set("border-radius", "999px")
            .set("background", "var(--lumo-base-color)")
            .set("border", "1px solid var(--lumo-contrast-10pct)");
        Span valueSpan = new Span(value);
        valueSpan.getStyle().set("font-weight", "700");
        Span labelSpan = new Span(label);
        labelSpan.getStyle().set("color", "var(--lumo-secondary-text-color)");
        item.add(valueSpan, labelSpan);
        return item;
    }

    private Div createInsightRow(String title, String value, String detail) {
        Div row = new Div();
        row.getStyle().set("padding", "var(--lumo-space-s) 0").set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        Span titleSpan = new Span(title);
        titleSpan.getStyle().set("display", "block").set("font-weight", "700");
        Span valueSpan = new Span(value);
        valueSpan.getStyle().set("display", "block").set("margin", "var(--lumo-space-xs) 0");
        Span detailSpan = new Span(detail);
        detailSpan.getStyle().set("color", "var(--lumo-secondary-text-color)").set("font-size", "0.9rem");
        row.add(titleSpan, valueSpan, detailSpan);
        return row;
    }

    private HorizontalLayout createActivityItem(String title, String value, String detail) {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.setPadding(false);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        row.getStyle().set("padding", "var(--lumo-space-s) 0").set("border-bottom", "1px solid var(--lumo-contrast-10pct)");
        Div left = new Div();
        Span titleSpan = new Span(title);
        titleSpan.getStyle().set("display", "block").set("font-weight", "700");
        Span detailSpan = new Span(detail);
        detailSpan.getStyle().set("display", "block").set("color", "var(--lumo-secondary-text-color)").set("font-size", "0.9rem");
        left.add(titleSpan, detailSpan);
        Span valueSpan = new Span(value);
        valueSpan.getStyle().set("font-weight", "600").set("text-align", "right");
        row.add(left, valueSpan);
        return row;
    }

    private Div createProgressBar(String label, String value, String color, int percent) {
        Div wrapper = new Div();
        wrapper.getStyle().set("margin-bottom", "var(--lumo-space-m)");
        HorizontalLayout top = new HorizontalLayout();
        top.setWidthFull();
        top.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        Span labelSpan = new Span(label);
        labelSpan.getStyle().set("font-weight", "600");
        Span valueSpan = new Span(value);
        valueSpan.getStyle().set("color", "var(--lumo-secondary-text-color)");
        top.add(labelSpan, valueSpan);
        Div track = new Div();
        track.getStyle()
            .set("height", "10px")
            .set("border-radius", "999px")
            .set("background", "var(--lumo-contrast-10pct)")
            .set("overflow", "hidden")
            .set("margin-top", "var(--lumo-space-xs)");
        Div fill = new Div();
        fill.getStyle().set("height", "100%").set("width", percent + "%").set("background", color).set("border-radius", "999px");
        track.add(fill);
        wrapper.add(top, track);
        return wrapper;
    }

    private Span createPill(String text, VaadinIcon icon, String color) {
        Span pill = new Span();
        pill.getStyle()
            .set("display", "inline-flex")
            .set("align-items", "center")
            .set("gap", "0.35rem")
            .set("padding", "0.35rem 0.7rem")
            .set("border-radius", "999px")
            .set("background", "var(--lumo-base-color)")
            .set("border", "1px solid var(--lumo-contrast-10pct)")
            .set("color", color)
            .set("font-size", "0.85rem");
        pill.add(new Icon(icon), new Span(text));
        return pill;
    }

    private void refreshDashboardData() {
        updateStatsDisplay(loadStats());
    }

    private Map<String, Object> loadStats() {
        UserRepository repository = userRepositoryProvider.getIfAvailable();
        if (repository == null) {
            updateConnectionState(false);
            return demoStats();
        }
        try {
            Optional<Map<String, Object>> stats = repository.getUserStats("current-user-id");
            updateConnectionState(true);
            return stats.orElseGet(this::demoStats);
        } catch (Exception e) {
            updateConnectionState(false);
            Notification.show("Unable to load dashboard data. Showing demo metrics.");
            return demoStats();
        }
    }

    private void updateConnectionState(boolean live) {
        if (connectionBadge != null) {
            connectionBadge.setText(live ? "Live data" : "Demo data");
        }
    }

    private Map<String, Object> demoStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("correct", 148);
        stats.put("wrong", 32);
        stats.put("answered", 180);
        stats.put("averageTimeMs", 4200L);
        stats.put("streakDays", 7);
        stats.put("unattempted", 20);
        return stats;
    }

    private void updateStatsDisplay(Map<String, Object> stats) {
        int correct = asInt(stats.get("correct"));
        int wrong = asInt(stats.get("wrong"));
        int answered = asInt(stats.get("answered"));
        int streakDays = asInt(stats.get("streakDays"));
        long averageTimeMs = asLong(stats.get("averageTimeMs"));
        double accuracy = answered > 0 ? (correct * 100.0) / answered : 0.0;

        setMetric("Total Correct", String.valueOf(correct));
        setMetric("Total Incorrect", String.valueOf(wrong));
        setMetric("Accuracy %", PERCENT_FORMAT.format(accuracy) + "%");
        setMetric("Average Time / Question", formatSeconds(averageTimeMs));
        setMetric("Current Streak", streakDays + " days");
        setMetric("Total Answered", String.valueOf(answered));

        if (lastUpdatedText != null) {
            lastUpdatedText.setText("Updated with " + (stats.containsKey("averageTimeMs") ? "live" : "sample") + " metrics");
        }
    }

    private void setMetric(String label, String value) {
        Span metric = metricValues.get(label);
        if (metric != null) {
            metric.setText(value);
        }
    }

    private int asInt(Object value) {
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }

    private long asLong(Object value) {
        return value instanceof Number ? ((Number) value).longValue() : 0L;
    }

    private String formatSeconds(long millis) {
        return String.format("%.1fs", millis / 1000.0);
    }

    private void toggleTheme() {
        ThemeManager.Theme current = themeManager.getCurrentTheme();
        ThemeManager.Theme next = current == ThemeManager.Theme.LIGHT ? ThemeManager.Theme.DARK : ThemeManager.Theme.LIGHT;
        themeManager.applyTheme(next);
        themeToggleButton.setIcon(new Icon(next == ThemeManager.Theme.LIGHT ? VaadinIcon.MOON : VaadinIcon.SUN_O));
        UI.getCurrent().getElement().setAttribute("theme", next.getValue());
    }
}
