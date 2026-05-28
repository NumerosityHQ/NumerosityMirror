package org.vaadin.numerosity.Featureset.MathEngine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlgebraOneTest {

    @Test
    void evaluateLinearFunctionUsesSlopeInterceptForm() {
        assertEquals(11.0, AlgebraOne.evaluateLinearFunction(3, 2, 3), 1e-9);
    }

    @Test
    void solveLinearSystemFindsUniqueIntersection() {
        double[] solution = AlgebraOne.solveLinearSystem(1, 1, 3, 1, -1, 1);
        assertArrayEquals(new double[] { 2.0, 1.0 }, solution, 1e-9);
    }

    @Test
    void quadraticVertexCalculationsAreConsistent() {
        assertEquals(1.0, AlgebraOne.quadraticVertexX(2, -4), 1e-9);
        assertEquals(-2.0, AlgebraOne.quadraticVertexY(2, -4, 0), 1e-9);
    }

    @Test
    void verticalSlopeReturnsNaN() {
        assertTrue(Double.isNaN(AlgebraOne.findSlope(1, 1, 1, 5)));
    }
}
