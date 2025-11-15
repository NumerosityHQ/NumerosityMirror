package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Algebra II mathematical operations.
 * Provides static methods for solving quadratic equations, evaluating exponentials, and computing logarithms.
 */
public class AlgebraTwo {

    /**
     * Solves the quadratic equation ax^2 + bx + c = 0 and returns the real roots.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term
     * @return an array of real roots; empty array if no real roots (discriminant < 0)
     */
    public static double[] solveQuadratic(double a, double b, double c) {
        double discriminant = Math.pow(b, 2) - 4 * a * c;
        if (discriminant < 0) return new double[] {};
        double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
        return new double[] { root1, root2 };
    }

    /**
     * Evaluates base raised to the power of exponent.
     *
     * @param base the base value
     * @param exponent the exponent value
     * @return base^exponent
     */
    public static double evaluateExponential(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Computes the logarithm of value with the given base.
     *
     * @param value the value to take the logarithm of
     * @param base the base of the logarithm
     * @return log_base(value)
     */
    public static double logBaseN(double value, double base) {
        return Math.log(value) / Math.log(base);
    }
}