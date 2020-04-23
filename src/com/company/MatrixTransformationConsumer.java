package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class MatrixTransformationConsumer extends Thread {

    private FileWriter fileWriter;
    private ThreadSafeQueue threadSafeQueue;
    private SharedControl sharedControl;

    public MatrixTransformationConsumer(FileWriter fileWriter, ThreadSafeQueue threadSafeQueue, SharedControl sharedControl) {
        this.fileWriter = fileWriter;
        this.threadSafeQueue = threadSafeQueue;
        this.sharedControl = sharedControl;
    }


    public void run() {
        while (true) {
            MatrixPair matrixPair = this.threadSafeQueue.pollMatrix();

            if (matrixPair == null) {
                System.out.println("The queue is dead");
                break;
            }

            float[][] result = this.matrixMultiplication(matrixPair);
            try {
                this.saveMatrixResult(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if (this.sharedControl.getMatrixCounter() == MatrixConstants.NUM_OF_MATRIX_PAIR / 2) {
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        this.sharedControl.increase();
    }

}
