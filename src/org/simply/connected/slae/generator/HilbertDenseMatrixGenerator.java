package org.simply.connected.slae.generator;

import org.simply.connected.slae.math.DenseMatrix;
import org.simply.connected.slae.math.SquareMatrix;

import java.util.List;

public class HilbertDenseMatrixGenerator extends AbstractSlaeGenerator {
    private static final List<Integer> arities =
            List.of(2, 3, 5, 10, 15, 20, 30, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000);

    @Override
    protected String getFileName(int arity, int matrixInd, String generationID) {
        return String.format("%s_%d", generationID, arity);
    }

    @Override
    protected List<SquareMatrix> getMatrices(int arity) {
        double[][] res = new double[arity][arity];
        for (int i = 0; i < arity; i++) {
            for (int j = 0; j < arity; j++) {
                res[i][j] = i + j + 1;
            }
        }
        return List.of(new DenseMatrix(res));
    }

    @Override
    protected List<Integer> getArities() {
        return arities;
    }
}
