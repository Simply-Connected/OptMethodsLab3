package org.simply.connected.slae.math;

import org.simply.connected.slae.generator.Utils;

import java.io.BufferedReader;
import java.io.IOException;

public class ProfileMatrix implements SquareMatrix {
    private final double[] diag;
    private final double[] aL;
    private final double[] aU;
    private final int[] ia;

    public ProfileMatrix(double[] diag, double[] aL, double[] aU, int[] inf) {
        this.diag = diag;
        this.aL = aL;
        this.aU = aU;
        this.ia = inf;
    }


    @Override
    public double get(int i, int j) {
        if (i == j) {
            return diag[i];
        }
        if (inUpper(i, j)) {
            return aU[getUpperInd(i, j)];
        }
        if (inLower(i, j)) {
            return aL[getLowerInd(i, j)];
        }
        return 0;
    }

    @Override
    public void set(int i, int j, double val) {
        if (!inUpper(i, j) && !inLower(i, j) && i != j) {
            throw new IllegalStateException(String.format("Cannot modify element: (%d, %d)", i, j));
        }
        if (i == j) {
            diag[i] = val;
        }
        if (inUpper(i, j)) {
            aU[getUpperInd(i, j)] = val;
        }
        if (inLower(i, j)) {
            aL[getLowerInd(i, j)] = val;
        }
    }

    @Override
    public int getArity() {
        return diag.length;
    }

    private int getLowerInd(int i, int j) {
        return ia[i] + j + getProfile(i) - i;
    }

    private int getUpperInd(int i, int j) {
        return getLowerInd(j, i);
    }

    private boolean inUpper(int i, int j) {
        return inLower(j, i);
    }

    private boolean inLower(int i, int j) {
            return i > j && j >= i - getProfile(i);
    }

    private int getProfile(int i) {
        return ia[i + 1] - ia[i];
    }

    public double[] getDiag() {
        return diag;
    }

    public double[] getLower() {
        return aL;
    }

    public double[] getUpper() {
        return aU;
    }

    public int[] getInf() {
        return ia;
    }

    @Override
    public String toString() {
        return String.format(
                "%s%n%s%n%s%n%s",
                Utils.joinToString(diag),
                Utils.joinToString(aL),
                Utils.joinToString(aU),
                Utils.joinToString(ia)
        );
    }

    public static ProfileMatrix readFrom(BufferedReader reader) throws IOException {
        return new ProfileMatrix(
                Utils.readDoubles(reader),
                Utils.readDoubles(reader),
                Utils.readDoubles(reader),
                Utils.readInts(reader)
        );
    }
}
