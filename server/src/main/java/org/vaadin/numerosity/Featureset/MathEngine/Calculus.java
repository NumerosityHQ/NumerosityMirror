package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Calculus mathematical operations.
 * Provides static methods for computing derivatives, definite integrals, and evaluating limits.
 */
public class Calculus {

    /**
     * Computes the derivative of the quadratic function ax^2 + bx + c at point x.
     * The derivative is 2ax + b.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term (not used in derivative)
     * @param x the point at which to evaluate the derivative
     * @return the derivative value 2ax + b
     */
    public static double derivativeAtPoint(double a, double b, double c, double x) {
        return 2 * a * x + b;
    }

    /**
     * Calculates the definite integral of ax^2 + bx + c from lower to upper bounds.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term
     * @param lower the lower bound of integration
     * @param upper the upper bound of integration
     * @return the definite integral value
     */
    public static double definiteIntegral(double a, double b, double c, double lower, double upper) {
        return (a / 3 * Math.pow(upper, 3) + b / 2 * Math.pow(upper, 2) + c * upper) -
               (a / 3 * Math.pow(lower, 3) + b / 2 * Math.pow(lower, 2) + c * lower);
    }

    /**
     * Evaluates the quadratic function ax^2 + bx + c as x approaches the given value.
     * This is essentially the function value at x.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term
     * @param x the value to approach
     * @return the function value a*x^2 + b*x + c
     */
    public static double limitApproaching(double a, double b, double c, double x) {
        return a * Math.pow(x, 2) + b * x + c;
    }
}