package org.simulacros.queue;

import org.simulacros.events.Action;
import org.simulacros.events.Event;
import org.simulacros.events.Scheduler;

public class SimpleQueue {

    private final QueueProperties queueProperties;
    private final Scheduler scheduler;

    private final double[] statesTime;

    private int clientsCount;
    private int lostClients = 0;

    public SimpleQueue(QueueProperties queueProperties, Scheduler scheduler, Integer firstArrival) {
        this.queueProperties = queueProperties;
        this.scheduler = scheduler;

        var event = new Event(Action.IN, firstArrival);
        scheduler.add(event);

        statesTime = new double[queueProperties.getQueueCapacity() + 1];
    }

    public void receiveClient(double time) {

        // Contabiliza o tempo
        statesTime[this.clientsCount] += time - scheduler.getLastExecuted();

        if (this.clientsCount < this.queueProperties.getQueueCapacity()) {
            // case para perda de cliente quand ofila cheia
            this.clientsCount++;

            if (clientsCount <= queueProperties.getAttendants()) {
                // agenda saida
                scheduler.scheduleExit(this.queueProperties, time);
            }
        } else {
            lostClients++;
        }
        scheduler.scheduleArrival(this.queueProperties, time);
    }

    public void serveClient(double time) {

        statesTime[clientsCount] += time - scheduler.getLastExecuted();

        this.clientsCount--;

        if (clientsCount >= 1) {
            scheduler.scheduleExit(this.queueProperties, time);
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



