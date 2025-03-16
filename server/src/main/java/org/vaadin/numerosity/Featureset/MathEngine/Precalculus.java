package org.vaadin.numerosity.Featureset.MathEngine;

public class Precalculus {
    public static double sine(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    public static double cosine(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public static double tangent(double angle) {
        return Math.tan(Math.toRadians(angle));
    }

    public static double factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }
}
