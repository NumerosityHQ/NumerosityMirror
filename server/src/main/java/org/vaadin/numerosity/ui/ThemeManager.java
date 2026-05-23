package org.vaadin.numerosity.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.server.VaadinService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Theme manager for handling dark/light mode and theme variants.
 * Provides a modern, minimalist aesthetic with smooth transitions.
 */
@Component
public class ThemeManager {

    public enum Theme {
        LIGHT("light"),
        DARK("dark");

        private final String value;

        Theme(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ThemeVariant {
        DEFAULT("default"),
        MATERIAL("material"),
        LUMO("lumo");

        private final String value;

        ThemeVariant(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final String THEME_ATTRIBUTE = "theme";
    private static final String TRANSITION_STYLE = "transition: background-color 0.3s ease, color 0.3s ease;";

    /**
     * Applies the specified theme to the current UI.
     *
     * @param theme the theme to apply
     */
    public void applyTheme(Theme theme) {
        if (UI.getCurrent() != null) {
            UI.getCurrent().getElement().setAttribute(THEME_ATTRIBUTE, theme.getValue());
            applyTransitionStyles();
        }
    }

    /**
     * Applies the specified theme variant.
     *
     * @param variant the theme variant to apply
     */
    public void applyThemeVariant(ThemeVariant variant) {
        UI.getCurrent().getPage().executeJs(
            "document.documentElement.setAttribute('data-theme-variant', $0);",
            variant.getValue()
        );
    }

    /**
     * Toggles between light and dark theme.
     *
     * @return the new theme
     */
    public Theme toggleTheme() {
        Theme current = getCurrentTheme();
        Theme newTheme = current == Theme.LIGHT ? Theme.DARK : Theme.LIGHT;
        applyTheme(newTheme);
        return newTheme;
    }

    /**
     * Gets the current theme.
     *
     * @return the current theme
     */
    public Theme getCurrentTheme() {
        String themeValue = UI.getCurrent().getElement().getAttribute(THEME_ATTRIBUTE);
        return Arrays.stream(Theme.values())
            .filter(t -> t.getValue().equals(themeValue))
            .findFirst()
            .orElse(Theme.LIGHT);
    }

    /**
     * Applies smooth transition styles to the document.
     */
    private void applyTransitionStyles() {
        UI.getCurrent().getPage().executeJs(
            "document.documentElement.style = $0;",
            TRANSITION_STYLE
        );
    }

    /**
     * Gets all available themes.
     *
     * @return list of available themes
     */
    public List<Theme> getAvailableThemes() {
        return Arrays.asList(Theme.values());
    }

    /**
     * Gets all available theme variants.
     *
     * @return list of available theme variants
     */
    public List<ThemeVariant> getAvailableThemeVariants() {
        return Arrays.asList(ThemeVariant.values());
    }
}