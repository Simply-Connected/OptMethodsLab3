package org.simply.connected.slae.methods;

import org.simply.connected.slae.math.*;

import java.io.File;
import java.io.IOException;

public interface SlaeSolver {
    Vector solve(SquareMatrix coefficients, Vector freeValues);

    Vector solveFile(File input, File output) throws IOException;
}
