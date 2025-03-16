package org.vaadin.numerosity.Featureset.MathEngine;

public class AlgebraTwo {
    public static double[] solveQuadratic(double a, double b, double c) {
        double discriminant = Math.pow(b, 2) - 4 * a * c;
        if (discriminant < 0) return new double[] {};
        double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
        return new double[] { root1, root2 };
    }

    public static double evaluateExponential(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public static double logBaseN(double value, double base) {
        return Math.log(value) / Math.log(base);
    }
}