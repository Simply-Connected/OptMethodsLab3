package org.simply.connected.slae.util;

import org.simply.connected.slae.generator.*;
import org.simply.connected.slae.math.Math;
import org.simply.connected.slae.math.Vector;
import org.simply.connected.slae.methods.GaussSlaeSolver;
import org.simply.connected.slae.methods.LUDecompositionSlaeSolver;
import org.simply.connected.slae.methods.SlaeSolver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SlaeTableResearchConstructor {
    public static final String researchDir =
            "C:\\Users\\okare\\IdeaProjects\\OptMethodsLab3\\Research";

    private static String researchCSV(SlaeSolver solver,
                                      Generator generator,
                                      StringBuilder sb,
                                      String solverID,
                                      String generationID) throws IOException {
        File dir = Path.of(researchDir.concat(String.format("\\%s\\%s", solverID, generationID))).toFile();
        dir.mkdirs();
        generator.generate(dir, generationID);
        File tempFile = File.createTempFile(generationID, "temp");
        Files.newDirectoryStream(dir.toPath()).forEach( path ->
                {
                    File file = path.toFile();
                    int[] nk = parseFileName(file);
                    int arity = nk[0];
                    Vector sol = AbstractSlaeGenerator.getSolution(arity);
                    Vector ans = null;
                    try {
                        ans = solver.solveFile(file, tempFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    double margin = Math.norm(Math.subtract(sol, ans));
                    String info = Arrays.stream(nk).mapToObj(String::valueOf).collect(Collectors.joining(","));
                    sb.append(String.format("%s,%.20f,%.20f%n", info, margin, margin / Math.norm(sol)));
                }
        );
        Path res = dir.toPath().getParent().resolve(generationID + "_RESULT");
        try(BufferedWriter writer = Files.newBufferedWriter(res)) {
            writer.write(sb.toString());
        }
        return sb.toString();
    }

   public static String researchLUCondCSV(double maxProfileSizeRatio) throws IOException {
        return researchCSV(new LUDecompositionSlaeSolver(),
                new ProfileMatrixGenerator(maxProfileSizeRatio),
                new StringBuilder("n, k, norm, norm\n"),
                "LU",
                "Cond");
    }

    public static String researchLUHilbertCSV() throws IOException {
        return researchCSV(new LUDecompositionSlaeSolver(),
                new HilbertMatrixGenerator(),
                new StringBuilder("n, norm, norm\n"),
                "LU",
                "Hilbert");
    }

    public static String researchGaussCondCSV() throws IOException {
        return researchCSV(new GaussSlaeSolver(),
                new DenseMatrixGenerator(),
                new StringBuilder("n, k, norm, norm\n"),
                "Gauss",
                "Cond");
    }

    private static int[] parseFileName(File file) {
        return Arrays.stream(file.getName().split("_")).skip(1).mapToInt(Integer::parseInt).toArray();
    }
}
