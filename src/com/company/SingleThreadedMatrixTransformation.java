package com.company;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringJoiner;

public class SingleThreadedMatrixTransformation {

    private final FileWriter fileWriter;
    private Scanner fileReader;

    public SingleThreadedMatrixTransformation(FileReader fileReader, FileWriter fileWriter) {
        this.fileReader = new Scanner(fileReader);
        this.fileWriter = fileWriter;
    }

    public void processMatrices() throws IOException {
        for (int i = 0; i < MatrixConstants.NUM_OF_MATRIX_PAIR; i++) {
            MatrixPair matrices = readMatrixPair();
            if (matrices != null) {
                this.saveMatrixResult(matrixMultiplication(matrices));
            }
        }
    }

    private MatrixPair readMatrixPair() {
        float[][] matrixA = readMatrix();
        float[][] matrixB = readMatrix();
        if (matrixA == null || matrixB == null) {
            return null;
        }
        return new MatrixPair(matrixA, matrixB);
    }

    private float[][] readMatrix() {
        float[][] matrix = new float[MatrixConstants.HEIGHT][MatrixConstants.WIDTH];
        for (int i = 0; i < MatrixConstants.HEIGHT; i++) {
            String stringRow = this.fileReader.nextLine();
            if (stringRow == null) {
                return null;
            }
            String[] rowItems = stringRow.split(", ");
            for (int y = 0; y < MatrixConstants.WIDTH; y++) {
                matrix[i][y] = Float.parseFloat(rowItems[y]);
            }
        }
        // strip matrix separator
        this.fileReader.nextLine();
        return matrix;
    }


    /**
     * a ↓  b →  c ↓
     * -------------
     * | A | B | C |
     * | D | E | F |
     * | G | H | I |
     */
    private float[][] matrixMultiplication(MatrixPair matrixPair) {
        float[][] m1 = matrixPair.getMatrix1();
        float[][] m2 = matrixPair.getMatrix2();

        float[][] result = new float[MatrixConstants.HEIGHT][MatrixConstants.WIDTH];
        for (int a = 0; a < MatrixConstants.WIDTH; a++) {
            for (int b = 0; b < MatrixConstants.WIDTH; b++) {
                for (int c = 0; c < MatrixConstants.WIDTH; c++) {
                    result[a][b] += m1[a][c] * m2[c][b];
                }
            }
        }
        return result;
    }

    private void saveMatrixResult(float[][] matrixResult) throws IOException {
        for (int i = 0; i < MatrixConstants.WIDTH; i++) {
            StringJoiner sj = new StringJoiner(", ");
            for (int y = 0; y < MatrixConstants.HEIGHT; y++) {
                sj.add(String.format("%.2f", matrixResult[i][y]));
            }
            this.fileWriter.write(sj.toString());
            this.fileWriter.write("\n");
        }
        this.fileWriter.write("\n");
    }
}
