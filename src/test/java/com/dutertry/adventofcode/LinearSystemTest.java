package com.dutertry.adventofcode;

import org.junit.Test;

import java.math.BigInteger;

public class LinearSystemTest {
    @Test
    public void testSolveLong() {
        long[][] coefficients = {
                {1, 1},
                {2, 1}
        };
        long[] constants = {1, 3};
        Rational[] solution = LinearSystem.solveSystem(coefficients, constants);
        assert solution[0].equals(new Rational(new BigInteger("2"), BigInteger.ONE));
        assert solution[1].equals(new Rational(new BigInteger("-1"), BigInteger.ONE));
    }

    @Test(expected = ArithmeticException.class)
    public void testSolveLongNoSolution() {
        long[][] coefficients = {
                {1, 1},
                {1, 1}
        };
        long[] constants = {1, 3};
        Rational[] solution = LinearSystem.solveSystem(coefficients, constants);
    }
}
