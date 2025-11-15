package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Algebra I mathematical operations.
 * Provides static methods for solving linear equations, evaluating quadratic expressions, and calculating slopes.
 */
public class AlgebraOne {

    /**
     * Solves the linear equation ax + b = 0 for x.
     *
     * @param a the coefficient of x
     * @param b the constant term
     * @return the solution x, or Double.NaN if a is zero (division by zero)
     */
    public static double solveLinear(double a, double b) {
        return a != 0 ? -b / a : Double.NaN;
    }

    /**
     * Evaluates the quadratic expression a*x^2 + b*x + c at the given x value.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term
     * @param x the value at which to evaluate the expression
     * @return the result of a*x^2 + b*x + c
     */
    public static double evaluateQuadratic(double a, double b, double c, double x) {
        return a * Math.pow(x, 2) + b * x + c;
    }

    /**
     * Calculates the slope between two points (x1, y1) and (x2, y2).
     *
     * @param x1 the x-coordinate of the first point
     * @param y1 the y-coordinate of the first point
     * @param x2 the x-coordinate of the second point
     * @param y2 the y-coordinate of the second point
     * @return the slope (y2 - y1) / (x2 - x1)
     */
    public static double findSlope(double x1, double y1, double x2, double y2) {
        return (y2 - y1) / (x2 - x1);
    }
}






