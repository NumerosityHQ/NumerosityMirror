package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Geometry mathematical operations.
 * Provides static methods for calculating areas, circumferences, and distances.
 */
public class Geometry {

    /**
     * Calculates the area of a triangle using the formula (1/2) * base * height.
     *
     * @param base the base length of the triangle
     * @param height the height of the triangle
     * @return the area of the triangle
     */
    public static double areaOfTriangle(double base, double height) {
        return 0.5 * base * height;
    }

    /**
     * Calculates the circumference of a circle using the formula 2 * π * radius.
     *
     * @param radius the radius of the circle
     * @return the circumference of the circle
     */
    public static double circumferenceOfCircle(double radius) {
        return 2 * Math.PI * radius;
    }

    /**
     * Calculates the Euclidean distance between two points (x1, y1) and (x2, y2).
     *
     * @param x1 the x-coordinate of the first point
     * @param y1 the y-coordinate of the first point
     * @param x2 the x-coordinate of the second point
     * @param y2 the y-coordinate of the second point
     * @return the distance between the two points
     */
    public static double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
