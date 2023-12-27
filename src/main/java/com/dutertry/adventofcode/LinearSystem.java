package com.dutertry.adventofcode;

import java.math.BigInteger;

public class LinearSystem {
    
    public static Rational[] solveSystem(long[][] coefficients, long[] constants) {
        int n = coefficients.length;
        Rational[][] rationalCoefficients = new Rational[n][n];
        Rational[] rationalConstants = new Rational[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rationalCoefficients[i][j] = new Rational(new BigInteger(String.valueOf(coefficients[i][j])), BigInteger.ONE);
            }
            rationalConstants[i] = new Rational(new BigInteger(String.valueOf(constants[i])), BigInteger.ONE);
        }
        
        return solveSystem(rationalCoefficients, rationalConstants);
    }

    public static Rational[] solveSystem(Rational[][] coefficients, Rational[] constants) {
        int n = coefficients.length;
        Rational[][] augmentedMatrix = new Rational[n][n + 1];
        Rational[] solution = new Rational[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = coefficients[i][j];
            }
            augmentedMatrix[i][n] = constants[i];
        }

        for (int k = 0; k < n - 1; k++) {
            int pivotRow = findPivotRow(augmentedMatrix, k);
            swapRows(augmentedMatrix, k, pivotRow);

            for (int i = k + 1; i < n; i++) {
                Rational factor = augmentedMatrix[i][k].divides(augmentedMatrix[k][k]);
                for (int j = k; j < n + 1; j++) {
                    augmentedMatrix[i][j] = augmentedMatrix[i][j].minus(factor.times(augmentedMatrix[k][j]));
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            Rational sum = Rational.ZERO;
            for (int j = i + 1; j < n; j++) {
                sum = sum.plus(augmentedMatrix[i][j].times(solution[j]));
            }
            solution[i] = augmentedMatrix[i][n].minus(sum).divides(augmentedMatrix[i][i]);
        }

        return solution;
    }

    private static int findPivotRow(Rational[][] matrix, int column) {
        int pivotRow = column;
        Rational maxValue = matrix[column][column].abs();

        for (int i = column + 1; i < matrix.length; i++) {
            Rational value = matrix[i][column].abs();
            if (value.compareTo(maxValue) > 0) {
                maxValue = value;
                pivotRow = i;
            }
        }

        return pivotRow;
    }

    private static void swapRows(Rational[][] matrix, int row1, int row2) {
        Rational[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }
}