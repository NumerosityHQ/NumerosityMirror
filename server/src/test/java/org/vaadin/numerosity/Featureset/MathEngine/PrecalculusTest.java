package org.vaadin.numerosity.Featureset.MathEngine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PrecalculusTest {

    @Test
    void reciprocalTrigFunctionsWork() {
        assertEquals(2.0, Precalculus.secant(60), 1e-9);
        assertEquals(1.0, Precalculus.cosecant(90), 1e-9);
        assertEquals(1.0, Precalculus.cotangent(45), 1e-9);
    }

    @Test
    void inverseTrigAndCoordinateConversionsWork() {
        assertEquals(30.0, Precalculus.inverseSineDegrees(0.5), 1e-9);
        assertArrayEquals(new double[] { 0.0, 1.0 }, Precalculus.unitCircleCoordinates(90), 1e-9);
        assertArrayEquals(new double[] { 0.0, 2.0 }, Precalculus.polarToCartesian(2, 90), 1e-9);
    }

    @Test
    void vectorAndCombinatoricsWork() {
        assertEquals(11.0, Precalculus.dotProduct(new double[] { 1, 2, 3 }, new double[] { 1, 2, 2 }), 1e-9);
        assertEquals(10.0, Precalculus.binomialCoefficient(5, 2), 1e-9);
        assertEquals(20.0, Precalculus.permutation(5, 2), 1e-9);
        assertEquals(120.0, Precalculus.factorial(5), 1e-9);
    }
}
