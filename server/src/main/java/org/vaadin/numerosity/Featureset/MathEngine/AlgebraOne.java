package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Algebra I mathematical operations.
 * Covers linear equations, systems, functions, and introductory quadratics.
 */
public class AlgebraOne {

    /**
     * Solves the linear equation ax + b = 0 for x.
     *
     * @param a the coefficient of x
     * @param b the constant term
     * @return the solution x, or Double.NaN if a is zero
     */
    public static double solveLinear(double a, double b) {
        return a != 0 ? -b / a : Double.NaN;
    }

    /**
     * Evaluates a linear function y = mx + b.
     *
     * @param slope the slope of the line
     * @param intercept the y-intercept
     * @param x the x value
     * @return the function value
     */
    public static double evaluateLinearFunction(double slope, double intercept, double x) {
        return slope * x + intercept;
    }

    /**
     * Finds the y-intercept of a line from a point and slope.
     *
     * @param slope the slope of the line
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @return the y-intercept
     */
    public static double findYIntercept(double slope, double x, double y) {
        return y - slope * x;
    }

    /**
     * Solves a 2x2 linear system:
     * a1x + b1y = c1
     * a2x + b2y = c2
     *
     * @return {x, y} if the system has one solution; otherwise an empty array
     */
    public static double[] solveLinearSystem(double a1, double b1, double c1, double a2, double b2, double c2) {
        double determinant = a1 * b2 - a2 * b1;
        if (determinant == 0) {
            return new double[] {};
        }

        double x = (c1 * b2 - c2 * b1) / determinant;
        double y = (a1 * c2 - a2 * c1) / determinant;
        return new double[] { x, y };
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
     * Calculates the discriminant of a quadratic expression.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term
     * @return the discriminant b^2 - 4ac
     */
    public static double calculateDiscriminant(double a, double b, double c) {
        return Math.pow(b, 2) - 4 * a * c;
    }

    /**
     * Finds the x-coordinate of the vertex of a quadratic function.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @return the x-coordinate of the vertex, or Double.NaN if a is zero
     */
    public static double quadraticVertexX(double a, double b) {
        return a != 0 ? -b / (2 * a) : Double.NaN;
    }

    /**
     * Finds the y-coordinate of the vertex of a quadratic function.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term
     * @return the y-coordinate of the vertex, or Double.NaN if the parabola is invalid
     */
    public static double quadraticVertexY(double a, double b, double c) {
        double vertexX = quadraticVertexX(a, b);
        return Double.isNaN(vertexX) ? Double.NaN : evaluateQuadratic(a, b, c, vertexX);
    }

    /**
     * Calculates the absolute value of a number.
     *
     * @param value the input value
     * @return the absolute value
     */
    public static double absoluteValue(double value) {
        return Math.abs(value);
    }

    /**
     * Calculates the slope between two points (x1, y1) and (x2, y2).
     *
     * @param x1 the x-coordinate of the first point
     * @param y1 the y-coordinate of the first point
     * @param x2 the x-coordinate of the second point
     * @param y2 the y-coordinate of the second point
     * @return the slope (y2 - y1) / (x2 - x1), or Double.NaN for a vertical line
     */
    public static double findSlope(double x1, double y1, double x2, double y2) {
        return x2 != x1 ? (y2 - y1) / (x2 - x1) : Double.NaN;
    }
}






