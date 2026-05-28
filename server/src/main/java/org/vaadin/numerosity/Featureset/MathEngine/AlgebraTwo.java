package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Algebra II mathematical operations.
 * Covers polynomials, quadratic and exponential functions, logarithms, and sequences.
 */
public class AlgebraTwo {

    /**
     * Solves the quadratic equation ax^2 + bx + c = 0 and returns the real roots.
     *
     * @param a the coefficient of x^2
     * @param b the coefficient of x
     * @param c the constant term
     * @return an array of real roots; empty array if no real roots or no unique quadratic solution
     */
    public static double[] solveQuadratic(double a, double b, double c) {
        if (a == 0) {
            double linearSolution = solveLinear(b, c);
            return Double.isNaN(linearSolution) ? new double[] {} : new double[] { linearSolution };
        }

        double discriminant = calculateDiscriminant(a, b, c);
        if (discriminant < 0) {
            return new double[] {};
        }

        double denominator = 2 * a;
        if (discriminant == 0) {
            return new double[] { -b / denominator };
        }

        double squareRoot = Math.sqrt(discriminant);
        double root1 = (-b + squareRoot) / denominator;
        double root2 = (-b - squareRoot) / denominator;
        return new double[] { root1, root2 };
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
     * Evaluates a polynomial using Horner's method.
     * Coefficients must be supplied from highest power to constant term.
     *
     * @param x the x value
     * @param coefficients the polynomial coefficients
     * @return the polynomial value
     */
    public static double evaluatePolynomial(double x, double... coefficients) {
        if (coefficients.length == 0) {
            return Double.NaN;
        }

        double result = 0;
        for (double coefficient : coefficients) {
            result = result * x + coefficient;
        }
        return result;
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
     * Computes the logarithm of a value with the given base.
     *
     * @param value the value to take the logarithm of
     * @param base the base of the logarithm
     * @return log_base(value), or Double.NaN for invalid inputs
     */
    public static double logBaseN(double value, double base) {
        if (value <= 0 || base <= 0 || base == 1) {
            return Double.NaN;
        }
        return Math.log(value) / Math.log(base);
    }

    /**
     * Solves for the exponent in base^x = value.
     *
     * @param base the base value
     * @param value the result value
     * @return the exponent x
     */
    public static double solveExponentialEquation(double base, double value) {
        return logBaseN(value, base);
    }

    /**
     * Finds the nth term of an arithmetic sequence.
     *
     * @param firstTerm the first term
     * @param difference the common difference
     * @param termNumber the term index, starting at 1
     * @return the nth term
     */
    public static double arithmeticSequenceTerm(double firstTerm, double difference, int termNumber) {
        return firstTerm + (termNumber - 1) * difference;
    }

    /**
     * Finds the sum of the first n terms of an arithmetic sequence.
     *
     * @param firstTerm the first term
     * @param difference the common difference
     * @param termNumber the number of terms
     * @return the sum of the sequence
     */
    public static double arithmeticSeriesSum(double firstTerm, double difference, int termNumber) {
        double lastTerm = arithmeticSequenceTerm(firstTerm, difference, termNumber);
        return termNumber * (firstTerm + lastTerm) / 2.0;
    }

    /**
     * Finds the nth term of a geometric sequence.
     *
     * @param firstTerm the first term
     * @param ratio the common ratio
     * @param termNumber the term index, starting at 1
     * @return the nth term
     */
    public static double geometricSequenceTerm(double firstTerm, double ratio, int termNumber) {
        return firstTerm * Math.pow(ratio, termNumber - 1);
    }

    /**
     * Finds the sum of the first n terms of a geometric sequence.
     *
     * @param firstTerm the first term
     * @param ratio the common ratio
     * @param termNumber the number of terms
     * @return the sum of the sequence
     */
    public static double geometricSeriesSum(double firstTerm, double ratio, int termNumber) {
        if (ratio == 1) {
            return firstTerm * termNumber;
        }
        return firstTerm * (1 - Math.pow(ratio, termNumber)) / (1 - ratio);
    }

    /**
     * Solves the linear equation ax + b = 0 for x.
     *
     * @param a the coefficient of x
     * @param b the constant term
     * @return the solution x, or Double.NaN if a is zero
     */
    private static double solveLinear(double a, double b) {
        return a != 0 ? -b / a : Double.NaN;
    }
}
