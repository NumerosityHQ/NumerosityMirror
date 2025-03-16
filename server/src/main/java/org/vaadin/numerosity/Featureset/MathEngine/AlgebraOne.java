package org.vaadin.numerosity.Featureset.MathEngine;

public class AlgebraOne {
    public static double solveLinear(double a, double b) {
        return a != 0 ? -b / a : Double.NaN;
    }

    public static double evaluateQuadratic(double a, double b, double c, double x) {
        return a * Math.pow(x, 2) + b * x + c;
    }

    public static double findSlope(double x1, double y1, double x2, double y2) {
        return (y2 - y1) / (x2 - x1);
    }
}






