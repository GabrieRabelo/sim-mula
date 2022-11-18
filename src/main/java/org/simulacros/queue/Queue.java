package org.simulacros.queue;

import org.simulacros.events.Scheduler;

import java.util.Arrays;

public class Queue {

    private final QueueProperties queueProperties;

    private final double[] statesTime;

    private int clientsCount;
    private int lostClients = 0;

    public Queue(QueueProperties queueProperties) {
        this.queueProperties = queueProperties;
        int statesCount;
        if (queueProperties.getQueueCapacity() < 0) {
            statesCount = 10;
        } else {
            statesCount = queueProperties.getQueueCapacity() + 1;
        }
        statesTime = new double[statesCount];

    }

    public QueueProperties getQueueProperties() {
        return queueProperties;
    }

    public void countTime(double now) {
        statesTime[this.clientsCount] += now - Scheduler.getInstance().getLastExecuted();
    }

    public void receiveClient(double now) {

        if (this.clientsCount < this.queueProperties.getQueueCapacity() || this.queueProperties.getQueueCapacity() == -1) {
            // case para perda de cliente quando fila cheia
            this.clientsCount++;

            if (clientsCount <= queueProperties.getAttendants()) {
                // agenda saida
                Scheduler.getInstance().schedulePassage(this.queueProperties, now);
            }
        } else {
            lostClients++;
        }
        Scheduler.getInstance().scheduleArrival(this.queueProperties, now);
    }


    public void passClientOut(double now) {
        this.clientsCount--;
        if (clientsCount >= this.queueProperties.getAttendants()) {
            Scheduler.getInstance().schedulePassage(this.queueProperties, now);
        }
    }

    public void passClientIn(double now) {
        if (clientsCount < this.queueProperties.getQueueCapacity()) {
            this.clientsCount++;
            if (this.clientsCount <= this.queueProperties.getAttendants()) {
                Scheduler.getInstance().scheduleExit(this.queueProperties, now);
            }
        } else {
            lostClients++;
        }
    }

    public void serveClient(double now) {

        this.clientsCount--;

        if (clientsCount >= queueProperties.getAttendants()) {
            Scheduler.getInstance().scheduleExit(this.queueProperties, now);
        }
    }

    // Array index is the number of clients
    public double[] getStatesTime() {
        return statesTime;
    }

    public int getLostClients() {
        return lostClients;
    }

    public double[] getProbabilities() {
        var probs = new double[statesTime.length];
        var total = 0D;

        for (double d : statesTime) {
            total += d;
        }

        assert total != 0;

        for (int i = 0; i < statesTime.length; i++) {
            probs[i] = statesTime[i] / total * 100;
        }

        return probs;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "ID = " + queueProperties.getQueueId() +
                "State Time = " + Arrays.toString(this.getStatesTime()) +
                "Probabilities = " + Arrays.toString(this.getProbabilities()) +
                "Lost cliente = " + this.getLostClients() +
                '}';
    }
}



