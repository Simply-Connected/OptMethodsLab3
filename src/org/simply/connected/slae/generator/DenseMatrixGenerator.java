package org.simply.connected.slae.generator;

import org.simply.connected.slae.math.DenseMatrix;
import org.simply.connected.slae.math.SquareMatrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DenseMatrixGenerator extends AbstractSlaeGenerator {

    private final Random random;
    private static final List<Integer> arities =
            List.of(10, 20, 30, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000);

    public DenseMatrixGenerator() {
        random = new Random(new Date().getTime());
    }

    @Override
    protected String getFileName(int arity, int matrixInd, String generationID) {
        return String.format("%s_%d_%d", generationID, arity, matrixInd);
    }

    @Override
    protected List<SquareMatrix> getMatrices(int arity) {
        double tenPow = 1;
        List<SquareMatrix> res = new ArrayList<>();
        for (int k = 0; k <= 10; k++) {
            double[][] matrix = new double[arity][arity];
            double sum = 0;
            for (int i = 0; i < arity; i++) {
                for (int j = 0; j < arity; j++) {
                    if (i == j) {
                        continue;
                    }
                    matrix[i][j] = -random.nextInt(5);
                    sum += matrix[i][j];
                }
            }
            matrix[0][0] = -sum + tenPow;
            for (int i = 1; i < arity; i++) {
                matrix[i][i] = -sum;
            }
            res.add(new DenseMatrix(matrix));
            tenPow /= 10;
        }
        return res;
    }


    @Override
    protected List<Integer> getArities() {
        return arities;
    }
}
