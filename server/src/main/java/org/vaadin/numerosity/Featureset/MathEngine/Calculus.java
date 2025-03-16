package org.vaadin.numerosity.Featureset.MathEngine;

public class Calculus {
    public static double derivativeAtPoint(double a, double b, double c, double x) {
        return 2 * a * x + b;
    }

    public static double definiteIntegral(double a, double b, double c, double lower, double upper) {
        return (a / 3 * Math.pow(upper, 3) + b / 2 * Math.pow(upper, 2) + c * upper) - 
               (a / 3 * Math.pow(lower, 3) + b / 2 * Math.pow(lower, 2) + c * lower);
    }

    public static double limitApproaching(double a, double b, double c, double x) {
        return a * Math.pow(x, 2) + b * x + c;
    }
}