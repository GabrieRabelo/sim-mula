package org.simulacros.queue;

import org.simulacros.generator.NumberGenerator;

import java.util.Stack;

public class SimpleQueue {

    private double globalTime;
    private final int arrivalStart;
    private final int arrivalEnd;
    private final int attendanceStart;
    private final int attendanceEnd;
    private final int queueCapacity;
    private int clientsCount;
    private final Stack<Double> randomNumbers = new Stack<>();

    public SimpleQueue(int arrivalStart, int arrivalEnd, int attendanceStart, int attendanceEnd, int queueCapacity, int randomNumberLength) {
        this.arrivalStart = arrivalStart;
        this.arrivalEnd = arrivalEnd;
        this.attendanceStart = attendanceStart;
        this.attendanceEnd = attendanceEnd;
        this.queueCapacity = queueCapacity;
        this.startStack(randomNumberLength);
    }

    public int getArrivalStart() {
        return arrivalStart;
    }

    public int getArrivalEnd() {
        return arrivalEnd;
    }

    public int getAttendanceStart() {
        return attendanceStart;
    }

    public int getAttendanceEnd() {
        return attendanceEnd;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public int getClientsCount() {
        return clientsCount;
    }

    public void receiveClient() {
        if (clientsCount < queueCapacity) {
            // case para perda de cliente quand ofila cheia
            this.clientsCount++;

            if (clientsCount <= 1) {
                //agenda sainda
                schdeuleExit();
            }
        }
        scheduleArrival();
    }

    private void scheduleArrival() {
        //método que agenda chegada do cliente

    }

    private void schdeuleExit() {
        //método que agenda saida do cliente
        double scheduledExitTime = (attendanceEnd - attendanceStart) * randomNumbers.pop() + attendanceStart;

    }

    private void startStack(int randomNumberLength) {
        NumberGenerator numberGenerator = new NumberGenerator(315, 745, 9999, 800);
        for (int i = 0; i < randomNumberLength; i++) {
            this.randomNumbers.push(numberGenerator.nextDouble());
        }
    }

    public void serveClient() {
        this.clientsCount--;
        if (clientsCount >= 1) {
            schdeuleExit();
        }
    }
}



