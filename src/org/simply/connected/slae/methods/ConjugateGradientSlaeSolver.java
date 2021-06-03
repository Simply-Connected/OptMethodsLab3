package org.simply.connected.slae.methods;

import org.simply.connected.slae.math.SquareMatrix;
import org.simply.connected.slae.math.ThinMatrix;
import org.simply.connected.slae.math.Vector;

import java.io.BufferedReader;
import java.io.IOException;

import static org.simply.connected.slae.math.Math.*;

public class ConjugateGradientSlaeSolver extends AbstractSlaeSolver {
    private static final int MAX_ITERATIONS = 1000;
    private final static double EPS = 1e-8;

    @Override
    public Vector solve(SquareMatrix A, Vector f) {
        Vector x = new Vector(f.getData());
        Vector r = subtract(f, product(A, x));
        Vector z = new Vector(r.getData());
        for (int i = 0; i < MAX_ITERATIONS && norm(r) / norm(f) >= EPS; i++) {
            Vector Az = product(A, z);
            double alpha = normSquare(r) / dotProduct(Az, z);
            x = add(x, product(alpha, z));
            Vector nextR = subtract(r, product(alpha, Az));
            double beta = normSquare(nextR) / normSquare(r);
            z = add(nextR, product(beta, z));
            r = nextR;
        }
        return x;
    }

    @Override
    protected SquareMatrix readMatrix(BufferedReader reader, int arity) throws IOException {
        return ThinMatrix.readFrom(reader);
    }
}
