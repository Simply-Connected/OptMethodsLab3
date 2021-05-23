package org.simply.connected.slae.generator;

import org.simply.connected.slae.math.ProfileMatrix;
import org.simply.connected.slae.math.SquareMatrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;
import static java.lang.Math.round;

public class ProfileMatrixLUDecompositionGenerator extends AbstractSlaeGenerator {
    private static final List<Integer> arities =
            List.of(10, 20, 30, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000);


    private final double maxProfileSizeRatio;
    private final Random random;

    public ProfileMatrixLUDecompositionGenerator(double maxProfileSizeRatio) {
        this.maxProfileSizeRatio = maxProfileSizeRatio;
        random = new Random(new Date().getTime());
    }

    private int[] getInf(int arity, int[] profiles) {
        int[] res = new int[arity + 1];
        res[0] = 0;
        res[1] = 0;
        for (int i = 2; i < arity + 1; i++) {
            res[i] = res[i - 1] + profiles[i - 1];
        }
        return res;
    }


    private int[] getProfiles(int arity) {
        int[] res = new int[arity];
        int maxProfile = (int) round(arity * maxProfileSizeRatio);

        for (int i = 0; i < arity; i++) {
            res[i] = random.nextInt(min(i, maxProfile) + 1);
        }
        return res;
    }

    private double[] getDiagonal(int arity, double sum) {
        double[] res = new double[arity];
        for (int i = 0; i < arity; i++) {
            res[i] = sum;
        }
        return res;
    }

    private double[] getTriangle(int arity, int[] profiles) {
        double[] res = new double[Utils.sum(profiles)];
        int curIndex = 0;
        for (int i = 0; i < arity; i++) {
            for (int j = 0; j < profiles[i]; j++) {
                res[curIndex++] = -random.nextInt(5);
            }
        }
        return res;
    }


    @Override
    protected String getFileName(int arity, int matrixInd, String generationID) {
        return String.format("%s_%d_%d", generationID, arity, matrixInd);
    }

    @Override
    protected List<SquareMatrix> getMatrices(int arity) {
        List<SquareMatrix> res = new ArrayList<>();
        int[] profiles = getProfiles(arity);
        int[] inf = getInf(arity, profiles);
        double[] aL = getTriangle(arity, profiles);
        double[] aU = getTriangle(arity, profiles);
        double tenPow = 1;
        for (int k = 0; k <= 10; k++) {
            double sum = Utils.sum(aL) + Utils.sum(aU);
            double[] diag = getDiagonal(arity, sum);
            diag[0] = -sum + tenPow;
            tenPow /= 10;
            ProfileMatrix matrix = new ProfileMatrix(diag, aL, aU, inf);
            res.add(matrix);
        }
        return res;
    }


    @Override
    protected List<Integer> getArities() {
        return arities;
    }
}
