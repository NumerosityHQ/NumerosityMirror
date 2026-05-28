package org.vaadin.numerosity.Featureset.MathEngine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GeometryTest {

    @Test
    void areaAndPerimeterFormulasWork() {
        assertEquals(12.0, Geometry.areaOfRectangle(3, 4), 1e-9);
        assertEquals(14.0, Geometry.perimeterOfRectangle(3, 4), 1e-9);
        assertEquals(4.0 * Math.PI, Geometry.areaOfCircle(2), 1e-9);
    }

    @Test
    void triangleAndCoordinateToolsWork() {
        assertEquals(6.0, Geometry.areaOfTriangle(3, 4), 1e-9);
        assertEquals(5.0, Geometry.pythagoreanTheorem(3, 4), 1e-9);
        assertArrayEquals(new double[] { 2.0, 3.0 }, Geometry.midpoint(0, 2, 4, 4), 1e-9);
    }

    @Test
    void solidsAndPolygonAnglesWork() {
        assertEquals(24.0, Geometry.volumeOfRectangularPrism(2, 3, 4), 1e-9);
        assertEquals(540.0, Geometry.sumOfInteriorAngles(5), 1e-9);
        assertEquals(108.0, Geometry.interiorAngleOfRegularPolygon(5), 1e-9);
        assertEquals(72.0, Geometry.exteriorAngleOfRegularPolygon(5), 1e-9);
    }
}
