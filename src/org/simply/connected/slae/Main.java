package org.simply.connected.slae;

import org.simply.connected.slae.math.DenseMatrix;
import org.simply.connected.slae.math.SquareMatrix;
import org.simply.connected.slae.math.Vector;
import org.simply.connected.slae.methods.ConjugateGradientSlaeSolver;

import static org.simply.connected.slae.math.Math.product;


public class Main {
    public static void main(String[] args)  {
        ConjugateGradientSlaeSolver solver = new ConjugateGradientSlaeSolver();
        SquareMatrix matrix = new DenseMatrix(
                new double[]{1, 1./2, 1./3},
                new double[]{1./2, 1./3, 1./4},
                new double[]{1./3  , 1./4, 1./5}
        );
        Vector f = product(matrix, new Vector(1, 2, 3));
        System.out.println(solver.solve(matrix, f));
        System.out.println(solver.getIterations());
    }
}
