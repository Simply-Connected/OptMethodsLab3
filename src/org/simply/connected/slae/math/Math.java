package org.simply.connected.slae.math;

public class Math {

    public static Vector product(SquareMatrix matrix, Vector vector) {
        validateArity(matrix.getArity(), vector.getArity());

        int arity = matrix.getArity();
        double[] res = new double[arity];
        for (int i = 0; i < arity; i++) {
            double sum = 0;
            for (int j = 0; j < arity; j++) {
                sum += matrix.get(i, j) * vector.get(j);
            }
            res[i] = sum;
        }
        return new Vector(res);
    }


    private static void validateArity(int a, int b) {
        if (a != b) {
            throw new IllegalArgumentException("Vector and matrix should have same arity");
        }
    }
}
