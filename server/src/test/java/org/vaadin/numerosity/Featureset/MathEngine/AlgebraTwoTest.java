package org.vaadin.numerosity.Featureset.MathEngine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AlgebraTwoTest {

    @Test
    void solveQuadraticReturnsRepeatedRootForPerfectSquare() {
        assertArrayEquals(new double[] { 2.0 }, AlgebraTwo.solveQuadratic(1, -4, 4), 1e-9);
    }

    @Test
    void solveQuadraticFallsBackToLinearWhenAIsZero() {
        assertArrayEquals(new double[] { 2.0 }, AlgebraTwo.solveQuadratic(0, 3, -6), 1e-9);
    }

    @Test
    void evaluatePolynomialUsesHornerOrder() {
        assertEquals(18.0, AlgebraTwo.evaluatePolynomial(2, 2, 3, 4), 1e-9);
    }

    @Test
    void arithmeticAndGeometricSequencesAreComputed() {
        assertEquals(14.0, AlgebraTwo.arithmeticSequenceTerm(2, 3, 5), 1e-9);
        assertEquals(26.0, AlgebraTwo.arithmeticSeriesSum(2, 3, 4), 1e-9);
        assertEquals(24.0, AlgebraTwo.geometricSequenceTerm(3, 2, 4), 1e-9);
        assertEquals(45.0, AlgebraTwo.geometricSeriesSum(3, 2, 4), 1e-9);
    }

    @Test
    void logBaseNUsesChangeOfBase() {
        assertEquals(3.0, AlgebraTwo.logBaseN(8, 2), 1e-9);
    }
}
