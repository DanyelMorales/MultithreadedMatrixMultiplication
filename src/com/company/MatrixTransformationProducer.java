package com.company;

import java.io.FileReader;
import java.util.Scanner;

public class MatrixTransformationProducer extends Thread {
    private Scanner fileReader;
    private ThreadSafeQueue threadSafeQueue;

    public MatrixTransformationProducer(FileReader fileReader, ThreadSafeQueue threadSafeQueue) {
        this.fileReader = new Scanner(fileReader);
        this.threadSafeQueue = threadSafeQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < MatrixConstants.NUM_OF_MATRIX_PAIR; i++) {
            MatrixPair matrices = readMatrixPair();
            if (matrices != null) {
                this.threadSafeQueue.addMatrix(matrices);
            }
        }
        this.threadSafeQueue.terminate();
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


}
