package com.company.Main;

import com.company.MatrixConstants;
import com.company.SingleThreadedMatrixTransformation;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SingleThreadedMain {
    public static void main(String[] args) throws IOException {
        FileReader input = new FileReader(MatrixConstants.INPUT);
        FileWriter output = new FileWriter(MatrixConstants.OUTPUT);
        SingleThreadedMatrixTransformation transformation = new SingleThreadedMatrixTransformation(input, output);

        long start = System.currentTimeMillis();
        transformation.processMatrices();
        input.close();
        output.flush();
        output.close();
        long finish = System.currentTimeMillis();
        long resultedTime = finish - start;
        System.out.println("Total time: " + resultedTime);
    }
}
