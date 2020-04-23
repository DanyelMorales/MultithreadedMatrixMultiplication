package com.company;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadSafeQueue {
    private Queue<MatrixPair> queue = new LinkedList();
    private boolean isTerminated = false;
    private boolean isEmpty = true;
    private static final int CAPACITY = 5;

    public synchronized void addMatrix(MatrixPair matricesPair) {
        while (this.queue.size() == CAPACITY) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        this.queue.add(matricesPair);
        this.isEmpty = false;
        notify();
    }

    public synchronized MatrixPair pollMatrix() {
        while (this.isEmpty && !isTerminated) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (this.queue.size() == 1) {
            this.isEmpty = true;
        } else if (this.queue.size() == 0 && isTerminated) {
            return null;
        }

        System.out.println("queue size " + queue.size());
        MatrixPair result = this.queue.poll();
        if (this.queue.size() < CAPACITY) {
            notifyAll();
        }
        return result;
    }

    public void terminate() {
        this.isEmpty = true;
        this.isTerminated = true;
    }
}
