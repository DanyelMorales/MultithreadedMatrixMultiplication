package com.company.Main;

import com.company.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MultiThreadedMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileReader input = new FileReader(MatrixConstants.INPUT);
        FileWriter output = new FileWriter(MatrixConstants.OUTPUT);
        ThreadSafeQueue queue = new ThreadSafeQueue();
        SharedControl sharedControl = new SharedControl();

        MatrixTransformationProducer producer = new MatrixTransformationProducer(input, queue);
        MatrixTransformationConsumer consumer = new MatrixTransformationConsumer(output, queue, sharedControl);
        MatrixTransformationConsumer consumer2 = new MatrixTransformationConsumer(output, queue, sharedControl);

        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        consumer2.start();
        producer.join();
        consumer.join();
        consumer2.join();
        long finish = System.currentTimeMillis();
        long resultedTime = finish - start;
        System.out.println("Total time: " + resultedTime);

    }


}
