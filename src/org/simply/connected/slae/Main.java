package org.simply.connected.slae;

import org.simply.connected.slae.generator.Generator;
import org.simply.connected.slae.generator.HilbertMatrixLUDecompositionGenerator;
import org.simply.connected.slae.generator.ProfileMatrixLUDecompositionGenerator;
import org.simply.connected.slae.methods.LUDecompositionSlaeSolver;
import org.simply.connected.slae.methods.SlaeSolver;

import java.io.IOException;
import java.nio.file.Path;


public class Main {
    public static void main(String[] args) throws IOException {
/*        Generator gen = new HilbertMatrixLUDecompositionGenerator();
        gen.generate(Path.of("C:\\Users\\okare\\IdeaProjects\\OptMethodsLab3\\Test").toFile(), "HILBERT");*/
        String path = "C:\\Users\\okare\\IdeaProjects\\OptMethodsLab3\\Test\\HILBERT_10";
        String solpath = "C:\\Users\\okare\\IdeaProjects\\OptMethodsLab3\\Test\\HILBERT_10_ans";
        SlaeSolver solver = new LUDecompositionSlaeSolver(1e-3);
        solver.solveFile(Path.of(path).toFile(), Path.of(solpath).toFile());
    }
}
