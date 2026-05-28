package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Precalculus mathematical operations.
 * Covers trigonometry, unit circle values, vectors, combinatorics, and polar/rectangular conversion.
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
     * Computes the secant of an angle given in degrees.
     *
     * @param angle the angle in degrees
     * @return the secant value, or Double.NaN if cosine is zero
     */
    public static double secant(double angle) {
        double cosineValue = cosine(angle);
        return cosineValue != 0 ? 1 / cosineValue : Double.NaN;
    }

    /**
     * Computes the cosecant of an angle given in degrees.
     *
     * @param angle the angle in degrees
     * @return the cosecant value, or Double.NaN if sine is zero
     */
    public static double cosecant(double angle) {
        double sineValue = sine(angle);
        return sineValue != 0 ? 1 / sineValue : Double.NaN;
    }

    /**
     * Computes the cotangent of an angle given in degrees.
     *
     * @param angle the angle in degrees
     * @return the cotangent value, or Double.NaN if tangent is zero
     */
    public static double cotangent(double angle) {
        double tangentValue = tangent(angle);
        return tangentValue != 0 ? 1 / tangentValue : Double.NaN;
    }

    /**
     * Computes the inverse sine in degrees.
     *
     * @param value the sine value
     * @return the angle in degrees
     */
    public static double inverseSineDegrees(double value) {
        return Math.toDegrees(Math.asin(value));
    }

    /**
     * Computes the inverse cosine in degrees.
     *
     * @param value the cosine value
     * @return the angle in degrees
     */
    public static double inverseCosineDegrees(double value) {
        return Math.toDegrees(Math.acos(value));
    }

    /**
     * Computes the inverse tangent in degrees.
     *
     * @param value the tangent value
     * @return the angle in degrees
     */
    public static double inverseTangentDegrees(double value) {
        return Math.toDegrees(Math.atan(value));
    }

    /**
     * Returns the unit circle coordinates for a given angle.
     *
     * @param angleDegrees the angle in degrees
     * @return {x, y} coordinates on the unit circle
     */
    public static double[] unitCircleCoordinates(double angleDegrees) {
        return new double[] { cosine(angleDegrees), sine(angleDegrees) };
    }

    /**
     * Converts polar coordinates to Cartesian coordinates.
     *
     * @param radius the radius
     * @param angleDegrees the angle in degrees
     * @return {x, y} Cartesian coordinates
     */
    public static double[] polarToCartesian(double radius, double angleDegrees) {
        return new double[] { radius * cosine(angleDegrees), radius * sine(angleDegrees) };
    }

    /**
     * Converts Cartesian coordinates to polar coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return {radius, angleDegrees}
     */
    public static double[] cartesianToPolar(double x, double y) {
        return new double[] { Math.hypot(x, y), Math.toDegrees(Math.atan2(y, x)) };
    }

    /**
     * Calculates the dot product of two vectors.
     *
     * @param firstVector the first vector
     * @param secondVector the second vector
     * @return the dot product, or Double.NaN if the vectors are invalid
     */
    public static double dotProduct(double[] firstVector, double[] secondVector) {
        if (firstVector == null || secondVector == null || firstVector.length != secondVector.length) {
            return Double.NaN;
        }

        double total = 0;
        for (int index = 0; index < firstVector.length; index++) {
            total += firstVector[index] * secondVector[index];
        }
        return total;
    }

    /**
     * Calculates the magnitude of a vector.
     *
     * @param vector the vector
     * @return the magnitude, or Double.NaN if the vector is invalid
     */
    public static double vectorMagnitude(double[] vector) {
        if (vector == null || vector.length == 0) {
            return Double.NaN;
        }

        double sumOfSquares = 0;
        for (double component : vector) {
            sumOfSquares += Math.pow(component, 2);
        }
        return Math.sqrt(sumOfSquares);
    }

    /**
     * Normalizes a vector to unit length.
     *
     * @param vector the vector
     * @return the normalized vector, or an empty array if the vector has zero magnitude
     */
    public static double[] normalizeVector(double[] vector) {
        double magnitude = vectorMagnitude(vector);
        if (Double.isNaN(magnitude) || magnitude == 0) {
            return new double[] {};
        }

        double[] normalizedVector = new double[vector.length];
        for (int index = 0; index < vector.length; index++) {
            normalizedVector[index] = vector[index] / magnitude;
        }
        return normalizedVector;
    }

    /**
     * Calculates the side length in the Law of Sines.
     *
     * @param knownSide the known side length
     * @param knownAngleDegrees the known angle in degrees
     * @param targetAngleDegrees the target angle in degrees
     * @return the unknown side length
     */
    public static double lawOfSinesSide(double knownSide, double knownAngleDegrees, double targetAngleDegrees) {
        double denominator = sine(knownAngleDegrees);
        return denominator != 0 ? knownSide * sine(targetAngleDegrees) / denominator : Double.NaN;
    }

    /**
     * Calculates the side length in the Law of Cosines.
     *
     * @param sideA the first side
     * @param sideB the second side
     * @param includedAngleDegrees the included angle in degrees
     * @return the third side length, or Double.NaN if the value is invalid
     */
    public static double lawOfCosinesSide(double sideA, double sideB, double includedAngleDegrees) {
        double radicand = Math.pow(sideA, 2) + Math.pow(sideB, 2)
                - 2 * sideA * sideB * cosine(includedAngleDegrees);
        return radicand >= 0 ? Math.sqrt(radicand) : Double.NaN;
    }

    /**
     * Calculates the binomial coefficient n choose k.
     *
     * @param n the number of items
     * @param k the number selected
     * @return the binomial coefficient
     */
    public static double binomialCoefficient(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            return Double.NaN;
        }

        k = Math.min(k, n - k);
        double result = 1;
        for (int index = 1; index <= k; index++) {
            result = result * (n - k + index) / index;
        }
        return result;
    }

    /**
     * Calculates a permutation count.
     *
     * @param n the total number of items
     * @param r the number selected
     * @return the permutation count
     */
    public static double permutation(int n, int r) {
        if (n < 0 || r < 0 || r > n) {
            return Double.NaN;
        }

        double result = 1;
        for (int index = 0; index < r; index++) {
            result *= (n - index);
        }
        return result;
    }

    /**
     * Calculates the factorial of n using recursion.
     *
     * @param n the non-negative integer
     * @return n! (factorial of n), or Double.NaN for negative inputs
     */
    public static double factorial(int n) {
        if (n < 0) {
            return Double.NaN;
        }
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }
}
