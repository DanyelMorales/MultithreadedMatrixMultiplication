package com.company.Main;

import com.company.MatrixConstants;
import com.company.MatrixGenerator;

import java.io.FileWriter;
import java.io.IOException;

public class MatrixGeneratorMain {
    public static void main(String[] args) throws IOException {
        FileWriter inputWriter = new FileWriter(MatrixConstants.INPUT);
        MatrixGenerator matrixGenerator = new MatrixGenerator(MatrixConstants.HEIGHT, MatrixConstants.WIDTH, MatrixConstants.MAX_NUM_OF_RANGE, inputWriter);
        matrixGenerator.generate(MatrixConstants.NUM_OF_MATRIX_PAIR);
        inputWriter.flush();
        inputWriter.close();
    }
}
