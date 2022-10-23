package org.simulacros.queue;

import org.simulacros.events.Scheduler;

public class Queue {

    private final QueueProperties queueProperties;
    private final Scheduler scheduler;

    private final double[] statesTime;

    private int clientsCount;
    private int lostClients = 0;

    public Queue(QueueProperties queueProperties, Scheduler scheduler) {
        this.queueProperties = queueProperties;
        this.scheduler = scheduler;
        statesTime = new double[queueProperties.getQueueCapacity() + 1];
    }

    public void countTime(double now) {
        statesTime[this.clientsCount] += now - scheduler.getLastExecuted();
    }

    public void receiveClient(double now) {

        if (this.clientsCount < this.queueProperties.getQueueCapacity()) {
            // case para perda de cliente quando fila cheia
            this.clientsCount++;

            if (clientsCount <= queueProperties.getAttendants()) {
                // agenda saida
                scheduler.schedulePassage(this.queueProperties, now);
            }
        } else {
            lostClients++;
        }
        scheduler.scheduleArrival(this.queueProperties, now);
    }


    public void passClientOut(double now) {
        this.clientsCount--;
        if (clientsCount >= this.queueProperties.getAttendants()) {
            scheduler.schedulePassage(this.queueProperties, now);
        }
    }

    public void passClientIn(double now) {
        if (clientsCount < this.queueProperties.getQueueCapacity()) {
            this.clientsCount++;
            if(this.clientsCount <= this.queueProperties.getAttendants()) {
                scheduler.scheduleExit(this.queueProperties, now);
            }
        } else {
            lostClients ++;
        }
    }

    public void serveClient(double now) {

        this.clientsCount--;

        if (clientsCount >= queueProperties.getAttendants()) {
            scheduler.scheduleExit(this.queueProperties, now);
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

        for (double d: statesTime) {
            total += d;
        }

        assert total != 0;

        for (int i = 0; i<statesTime.length; i++) {
            probs[i] = statesTime[i] / total * 100;
        }

        return probs;
    }
}



