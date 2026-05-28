package org.vaadin.numerosity.Featureset.MathEngine;

/**
 * Utility class for Geometry mathematical operations.
 * Covers plane geometry, coordinate geometry, and 2D/3D measurement formulas.
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
     * Calculates the area of a rectangle.
     *
     * @param length the rectangle length
     * @param width the rectangle width
     * @return the area
     */
    public static double areaOfRectangle(double length, double width) {
        return length * width;
    }

    /**
     * Calculates the perimeter of a rectangle.
     *
     * @param length the rectangle length
     * @param width the rectangle width
     * @return the perimeter
     */
    public static double perimeterOfRectangle(double length, double width) {
        return 2 * (length + width);
    }

    /**
     * Calculates the area of a circle.
     *
     * @param radius the radius of the circle
     * @return the area
     */
    public static double areaOfCircle(double radius) {
        return Math.PI * Math.pow(radius, 2);
    }

    /**
     * Calculates the circumference of a circle using the formula 2 * pi * radius.
     *
     * @param radius the radius of the circle
     * @return the circumference of the circle
     */
    public static double circumferenceOfCircle(double radius) {
        return 2 * Math.PI * radius;
    }

    /**
     * Calculates the area of a parallelogram.
     *
     * @param base the base length
     * @param height the height
     * @return the area
     */
    public static double areaOfParallelogram(double base, double height) {
        return base * height;
    }

    /**
     * Calculates the area of a trapezoid.
     *
     * @param baseOne one parallel side
     * @param baseTwo the other parallel side
     * @param height the height
     * @return the area
     */
    public static double areaOfTrapezoid(double baseOne, double baseTwo, double height) {
        return 0.5 * (baseOne + baseTwo) * height;
    }

    /**
     * Calculates the perimeter of a triangle.
     *
     * @param sideA the first side
     * @param sideB the second side
     * @param sideC the third side
     * @return the perimeter
     */
    public static double perimeterOfTriangle(double sideA, double sideB, double sideC) {
        return sideA + sideB + sideC;
    }

    /**
     * Calculates a triangle's area using Heron's formula.
     *
     * @param sideA the first side
     * @param sideB the second side
     * @param sideC the third side
     * @return the area, or Double.NaN if the sides do not form a triangle
     */
    public static double triangleAreaByHeron(double sideA, double sideB, double sideC) {
        double semiPerimeter = perimeterOfTriangle(sideA, sideB, sideC) / 2.0;
        double radicand = semiPerimeter * (semiPerimeter - sideA) * (semiPerimeter - sideB) * (semiPerimeter - sideC);
        return radicand >= 0 ? Math.sqrt(radicand) : Double.NaN;
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

    /**
     * Finds the midpoint between two points.
     *
     * @param x1 the first x-coordinate
     * @param y1 the first y-coordinate
     * @param x2 the second x-coordinate
     * @param y2 the second y-coordinate
     * @return {x, y} for the midpoint
     */
    public static double[] midpoint(double x1, double y1, double x2, double y2) {
        return new double[] { (x1 + x2) / 2.0, (y1 + y2) / 2.0 };
    }

    /**
     * Calculates the slope of a line through two points.
     *
     * @param x1 the first x-coordinate
     * @param y1 the first y-coordinate
     * @param x2 the second x-coordinate
     * @param y2 the second y-coordinate
     * @return the slope, or Double.NaN for a vertical line
     */
    public static double slopeOfLine(double x1, double y1, double x2, double y2) {
        return x2 != x1 ? (y2 - y1) / (x2 - x1) : Double.NaN;
    }

    /**
     * Calculates the Pythagorean theorem result c = sqrt(a^2 + b^2).
     *
     * @param legA the first leg
     * @param legB the second leg
     * @return the hypotenuse
     */
    public static double pythagoreanTheorem(double legA, double legB) {
        return Math.sqrt(Math.pow(legA, 2) + Math.pow(legB, 2));
    }

    /**
     * Calculates the length of an arc.
     *
     * @param radius the circle radius
     * @param angleDegrees the central angle in degrees
     * @return the arc length
     */
    public static double arcLength(double radius, double angleDegrees) {
        return circumferenceOfCircle(radius) * (angleDegrees / 360.0);
    }

    /**
     * Calculates the area of a sector.
     *
     * @param radius the circle radius
     * @param angleDegrees the central angle in degrees
     * @return the sector area
     */
    public static double sectorArea(double radius, double angleDegrees) {
        return areaOfCircle(radius) * (angleDegrees / 360.0);
    }

    /**
     * Calculates the volume of a cube.
     *
     * @param side the side length
     * @return the volume
     */
    public static double volumeOfCube(double side) {
        return Math.pow(side, 3);
    }

    /**
     * Calculates the volume of a rectangular prism.
     *
     * @param length the length
     * @param width the width
     * @param height the height
     * @return the volume
     */
    public static double volumeOfRectangularPrism(double length, double width, double height) {
        return length * width * height;
    }

    /**
     * Calculates the surface area of a rectangular prism.
     *
     * @param length the length
     * @param width the width
     * @param height the height
     * @return the surface area
     */
    public static double surfaceAreaOfRectangularPrism(double length, double width, double height) {
        return 2 * (length * width + length * height + width * height);
    }

    /**
     * Calculates the volume of a cylinder.
     *
     * @param radius the radius
     * @param height the height
     * @return the volume
     */
    public static double volumeOfCylinder(double radius, double height) {
        return areaOfCircle(radius) * height;
    }

    /**
     * Calculates the surface area of a cylinder.
     *
     * @param radius the radius
     * @param height the height
     * @return the surface area
     */
    public static double surfaceAreaOfCylinder(double radius, double height) {
        return 2 * Math.PI * radius * (radius + height);
    }

    /**
     * Calculates the volume of a sphere.
     *
     * @param radius the radius
     * @return the volume
     */
    public static double volumeOfSphere(double radius) {
        return 4.0 / 3.0 * Math.PI * Math.pow(radius, 3);
    }

    /**
     * Calculates the surface area of a sphere.
     *
     * @param radius the radius
     * @return the surface area
     */
    public static double surfaceAreaOfSphere(double radius) {
        return 4 * Math.PI * Math.pow(radius, 2);
    }

    /**
     * Calculates the sum of the interior angles of a polygon.
     *
     * @param sides the number of sides
     * @return the angle sum, or Double.NaN if the polygon is invalid
     */
    public static double sumOfInteriorAngles(int sides) {
        return sides >= 3 ? (sides - 2) * 180.0 : Double.NaN;
    }

    /**
     * Calculates one interior angle of a regular polygon.
     *
     * @param sides the number of sides
     * @return one interior angle, or Double.NaN if the polygon is invalid
     */
    public static double interiorAngleOfRegularPolygon(int sides) {
        double sum = sumOfInteriorAngles(sides);
        return Double.isNaN(sum) ? Double.NaN : sum / sides;
    }

    /**
     * Calculates one exterior angle of a regular polygon.
     *
     * @param sides the number of sides
     * @return one exterior angle, or Double.NaN if the polygon is invalid
     */
    public static double exteriorAngleOfRegularPolygon(int sides) {
        return sides >= 3 ? 360.0 / sides : Double.NaN;
    }
}
