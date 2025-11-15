package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Precalculus mathematical operations.
 * Provides static methods for trigonometric functions and factorial calculation.
 */
public class Precalculus {

    /**
     * Computes the sine of an angle given in degrees.
     *
     * @param angle the angle in degrees
     * @return the sine value
     */
    public static double sine(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    /**
     * Computes the cosine of an angle given in degrees.
     *
     * @param angle the angle in degrees
     * @return the cosine value
     */
    public static double cosine(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    /**
     * Computes the tangent of an angle given in degrees.
     *
     * @param angle the angle in degrees
     * @return the tangent value
     */
    public static double tangent(double angle) {
        return Math.tan(Math.toRadians(angle));
    }

    /**
     * Calculates the factorial of n using recursion.
     *
     * @param n the non-negative integer
     * @return n! (factorial of n)
     */
    public static double factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }
}
