package org.vaadin.numerosity.Featureset.MathEngine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculusTest {

    @Test
    void derivativeAndRuleHelpersWork() {
        assertEquals(12.0, Calculus.powerRuleDerivative(3, 2, 2), 1e-9);
        assertEquals(22.0, Calculus.productRuleDerivative(2, 3, 4, 5), 1e-9);
        assertEquals(0.25, Calculus.quotientRuleDerivative(2, 1, 4, 0), 1e-9);
        assertEquals(6.0, Calculus.chainRuleDerivative(2, 3), 1e-9);
    }

    @Test
    void polynomialDerivativeAndIntegralWork() {
        assertEquals(14.0, Calculus.polynomialDerivative(2, 3, 2, 1), 1e-9);
        assertEquals(3.0, Calculus.polynomialIntegral(0, 1, 3, 2, 1), 1e-9);
        assertEquals(2.0, Calculus.averageRateOfChange(1, 5, 1, 3), 1e-9);
    }

    @Test
    void approximationAndLimitHelpersWork() {
        assertEquals(3.0, Calculus.leftRiemannSum(new double[] { 1, 2, 3 }, 1), 1e-9);
        assertEquals(5.0, Calculus.rightRiemannSum(new double[] { 1, 2, 3 }, 1), 1e-9);
        assertEquals(4.0, Calculus.trapezoidalRule(new double[] { 1, 2, 3 }, 1), 1e-9);
        assertEquals(1.0, Calculus.newtonRaphsonStep(3, 4, 2), 1e-9);
        assertEquals(4.0, Calculus.limitApproaching(1, 0, 0, 2), 1e-9);
    }
}
