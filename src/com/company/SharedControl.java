package com.company;

public class SharedControl {
    private int matrixCounter = 0;

    public synchronized int getMatrixCounter() {
        return matrixCounter;
    }

    public synchronized void increase() {
        this.matrixCounter++;
    }
}
