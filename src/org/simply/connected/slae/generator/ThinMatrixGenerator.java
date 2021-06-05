package org.simply.connected.slae.generator;

import org.simply.connected.slae.math.SquareMatrix;
import org.simply.connected.slae.math.ThinMatrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;
import static java.lang.Math.round;

public class ThinMatrixGenerator extends AbstractSlaeGenerator {
    private static final List<Integer> arities =
            List.of(10, 20, 30, 50, 100, 200, 300, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000);
    private static final int MAX_K = 10;

    private final double maxPortraitSizeRatio;
    private final Random random;

    public ThinMatrixGenerator(double maxPortraitSizeRatio) {
        if (maxPortraitSizeRatio <= 0 || maxPortraitSizeRatio > 1) {
            throw new IllegalStateException("Max Portrait size should be between 0 and 1");
        }
        this.maxPortraitSizeRatio = maxPortraitSizeRatio;
        random = new Random(new Date().getTime());
    }

    protected int[] getInf(int arity, int[] portrait) {
        int[] res = new int[arity + 1];
        res[0] = 0;
        res[1] = 0;
        for (int i = 2; i < arity + 1; i++) {
            res[i] = res[i - 1] + portrait[i - 1];
        }
        return res;
    }


    private int[] getJA(int arity, int[] portrait) {
        int[] res = new int[Utils.sum(portrait)];
        int ind = 0;
        for (int i = 0; i < arity; i++) {
            for (int rand: random.ints(0, i + 1).distinct().limit(portrait[i]).sorted().toArray()) {
                res[ind++] = rand;
            }
        }
        return res;
    }

    protected int[] getPortrait(int arity) {
        int[] res = new int[arity];
        int maxPortrait = (int) round(arity * maxPortraitSizeRatio);

        for (int i = 0; i < arity; i++) {
            res[i] = random.nextInt(min(i, maxPortrait) + 1);
        }
        return res;
    }

    protected double[] getDiagonal(int arity, double sum) {
        double[] res = new double[arity];
        for (int i = 0; i < arity; i++) {
            res[i] =  -sum;
        }
        return res;
    }

    protected double[] getTriangle(int arity, int[] portrait) {
        double[] res = new double[Utils.sum(portrait)];
        int curIndex = 0;
        for (int i = 0; i < arity; i++) {
            for (int j = 0; j < portrait[i]; j++) {
                res[curIndex++] = (random.nextInt(4) + 1);
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
        int[] portrait = getPortrait(arity);
        int[] inf = getInf(arity, portrait);
        int[] ja = getJA(arity, portrait);
        double[] aL = getTriangle(arity, portrait);
        double[] aU = getTriangle(arity, portrait);
        double tenPow = 1;
        double sum = Utils.sum(aL) + Utils.sum(aU);
        for (int k = 0; k <= MAX_K; k++) {
            double[] diag = getDiagonal(arity, sum);
            diag[0] = -sum + tenPow;
            tenPow /= 10;
            ThinMatrix matrix = new ThinMatrix(diag, aL, aU, inf, ja);
            res.add(matrix);
        }
        return res;
    }

    @Override
    protected List<Integer> getArities() {
        return arities;
    }
}
