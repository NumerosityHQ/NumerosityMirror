package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Calculus mathematical operations.
 * Covers differentiation, integration, rates of change, limits, and numerical approximation.
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
     * Computes the derivative of a power function ax^n at point x.
     *
     * @param coefficient the leading coefficient
     * @param exponent the exponent
     * @param x the point at which to evaluate the derivative
     * @return the derivative value
     */
    public static double powerRuleDerivative(double coefficient, double exponent, double x) {
        return coefficient * exponent * Math.pow(x, exponent - 1);
    }

    /**
     * Computes the derivative of a polynomial at a point.
     * Coefficients must be supplied from highest power to constant term.
     *
     * @param x the point at which to evaluate the derivative
     * @param coefficients the polynomial coefficients
     * @return the derivative value, or Double.NaN if the polynomial is invalid
     */
    public static double polynomialDerivative(double x, double... coefficients) {
        if (coefficients.length == 0) {
            return Double.NaN;
        }
        if (coefficients.length == 1) {
            return 0;
        }

        double value = coefficients[0];
        double derivative = 0;
        for (int index = 1; index < coefficients.length; index++) {
            derivative = derivative * x + value;
            value = value * x + coefficients[index];
        }
        return derivative;
    }

    /**
     * Applies the product rule to two functions at a point.
     *
     * @param u the first function value
     * @param duDx the derivative of the first function
     * @param v the second function value
     * @param dvDx the derivative of the second function
     * @return the derivative of uv
     */
    public static double productRuleDerivative(double u, double duDx, double v, double dvDx) {
        return duDx * v + u * dvDx;
    }

    /**
     * Applies the quotient rule to two functions at a point.
     *
     * @param u the numerator function value
     * @param duDx the derivative of the numerator
     * @param v the denominator function value
     * @param dvDx the derivative of the denominator
     * @return the derivative of u/v, or Double.NaN if the denominator is zero
     */
    public static double quotientRuleDerivative(double u, double duDx, double v, double dvDx) {
        return v != 0 ? (duDx * v - u * dvDx) / Math.pow(v, 2) : Double.NaN;
    }

    /**
     * Applies the chain rule to nested functions at a point.
     *
     * @param outerDerivative the derivative of the outer function
     * @param innerDerivative the derivative of the inner function
     * @return the derivative of the composition
     */
    public static double chainRuleDerivative(double outerDerivative, double innerDerivative) {
        return outerDerivative * innerDerivative;
    }

    /**
     * Calculates the average rate of change between two points.
     *
     * @param startValue the first function value
     * @param endValue the second function value
     * @param startX the first x value
     * @param endX the second x value
     * @return the average rate of change, or Double.NaN if the x values match
     */
    public static double averageRateOfChange(double startValue, double endValue, double startX, double endX) {
        return endX != startX ? (endValue - startValue) / (endX - startX) : Double.NaN;
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
        return polynomialIntegral(lower, upper, a, b, c);
    }

    /**
     * Computes a definite integral for a polynomial.
     * Coefficients must be supplied from highest power to constant term.
     *
     * @param lower the lower bound
     * @param upper the upper bound
     * @param coefficients the polynomial coefficients
     * @return the area under the curve, or Double.NaN if the polynomial is invalid
     */
    public static double polynomialIntegral(double lower, double upper, double... coefficients) {
        if (coefficients.length == 0) {
            return Double.NaN;
        }

        double result = 0;
        int degree = coefficients.length - 1;
        for (int index = 0; index < coefficients.length; index++) {
            int power = degree - index;
            double coefficient = coefficients[index];
            result += coefficient * (Math.pow(upper, power + 1) - Math.pow(lower, power + 1)) / (power + 1);
        }
        return result;
    }

    /**
     * Approximates the integral using a left Riemann sum.
     * The input array should contain sampled values at the partition points.
     *
     * @param functionValues the sampled function values
     * @param width the width of each subinterval
     * @return the approximation
     */
    public static double leftRiemannSum(double[] functionValues, double width) {
        if (functionValues == null || functionValues.length < 2) {
            return Double.NaN;
        }

        double total = 0;
        for (int index = 0; index < functionValues.length - 1; index++) {
            total += functionValues[index];
        }
        return total * width;
    }

    /**
     * Approximates the integral using a right Riemann sum.
     * The input array should contain sampled values at the partition points.
     *
     * @param functionValues the sampled function values
     * @param width the width of each subinterval
     * @return the approximation
     */
    public static double rightRiemannSum(double[] functionValues, double width) {
        if (functionValues == null || functionValues.length < 2) {
            return Double.NaN;
        }

        double total = 0;
        for (int index = 1; index < functionValues.length; index++) {
            total += functionValues[index];
        }
        return total * width;
    }

    /**
     * Approximates the integral using the trapezoidal rule.
     *
     * @param functionValues the sampled function values
     * @param width the width of each subinterval
     * @return the approximation
     */
    public static double trapezoidalRule(double[] functionValues, double width) {
        if (functionValues == null || functionValues.length < 2) {
            return Double.NaN;
        }

        double total = (functionValues[0] + functionValues[functionValues.length - 1]) / 2.0;
        for (int index = 1; index < functionValues.length - 1; index++) {
            total += functionValues[index];
        }
        return total * width;
    }

    /**
     * Applies one Newton-Raphson iteration.
     *
     * @param currentEstimate the current approximation
     * @param functionValue the function value at the current approximation
     * @param derivativeValue the derivative value at the current approximation
     * @return the next approximation, or Double.NaN if the derivative is zero
     */
    public static double newtonRaphsonStep(double currentEstimate, double functionValue, double derivativeValue) {
        return derivativeValue != 0 ? currentEstimate - functionValue / derivativeValue : Double.NaN;
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