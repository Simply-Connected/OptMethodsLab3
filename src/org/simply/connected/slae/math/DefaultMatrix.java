package org.simply.connected.slae.math;

public class DefaultMatrix implements SquareMatrix{
    private final double[][] data;

    public DefaultMatrix(double[]... data) {
        this.data = data;
    }


    @Override
    public double get(int i, int j) {
        return data[i][j];
    }

    @Override
    public void set(int i, int j, double val) {
        data[i][j] = val;
    }

    @Override
    public int getArity() {
        return data.length;
    }
}
