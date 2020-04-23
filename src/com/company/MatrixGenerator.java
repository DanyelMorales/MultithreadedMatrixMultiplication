package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringJoiner;

public class MatrixGenerator {
    private Random random = new Random();
    private FileWriter fileWriter;
    private int height;
    private int width;
    private int maxNumRange;

    public MatrixGenerator(int height, int width, int maxNumRange, FileWriter fileWriter) {
        this.fileWriter = fileWriter;
        this.height = height;
        this.width = width;
        this.maxNumRange = maxNumRange;
    }

    public ArrayList<float[][]> generate(int numberOfPairs) throws IOException {
        ArrayList<float[][]> result = this.generateMatrixPair(numberOfPairs);
        for (float[][] matrix : result) {
            this.writeMatrixToFile(matrix);
        }
        return result;
    }

    private ArrayList<float[][]> generateMatrixPair(int numberOfPairs) {
        ArrayList<float[][]> result = new ArrayList<>();
        for (int i = 0; i < numberOfPairs; i++) {
            float[][] matrixA = this.generateSingleMatrix();
            float[][] matrixB = this.generateSingleMatrix();
            result.add(matrixA);
            result.add(matrixB);
        }
        return result;
    }

    private float[] generateMatrixRow() {
        float[] row = new float[width];
        for (int y = 0; y < width; y++) {
            row[y] = random.nextFloat() * random.nextInt(this.maxNumRange);
        }
        return row;
    }

    private float[][] generateSingleMatrix() {
        float[][] result = new float[height][width];
        for (int i = 0; i < height; i++) {
            result[i] = generateMatrixRow();
        }
        return result;
    }

    private void writeMatrixToFile(float[][] matrix) throws IOException {
        for (int i = 0; i < this.height; i++) {
            StringJoiner sj = new StringJoiner(", ");
            for (int y = 0; y < this.width; y++) {
                sj.add(String.format("%.2f", matrix[i][y]));
            }
            fileWriter.write(sj.toString());
            fileWriter.write("\n");
        }
        fileWriter.write("\n");
    }
}
