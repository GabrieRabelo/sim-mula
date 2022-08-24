package org.simulacros.queue;

import java.util.Stack;

public class SimpleQueue {

    private double globalTime;
    private final QueueProperties queueProperties;
    private final Stack<Double> randomNumbers;

    private int clientsCount;

    public SimpleQueue(QueueProperties queueProperties, Stack<Double> randomNumbers) {
        this.queueProperties = queueProperties;
        this.randomNumbers = randomNumbers;
    }

    public double getGlobalTime() {
        return globalTime;
    }

    public int getClientsCount() {
        return clientsCount;
    }

    public void receiveClient() {
        if (clientsCount < queueProperties.getQueueCapacity()) {
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
        double scheduledExitTime = (queueProperties.getAttendanceEnd() - queueProperties.getAttendanceStart()) * randomNumbers.pop() + queueProperties.getAttendanceStart();

    }

    public void serveClient() {
        this.clientsCount--;
        if (clientsCount >= 1) {
            schdeuleExit();
        }
    }
}



