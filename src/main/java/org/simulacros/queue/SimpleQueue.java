package org.simulacros.queue;

import org.simulacros.events.Action;
import org.simulacros.events.Event;
import org.simulacros.events.Events;

import java.util.Stack;

public class SimpleQueue {

    private final QueueProperties queueProperties;
    private final Stack<Double> randomNumbers;
    private final Events events;

    private final double[] statesTime;

    private int clientsCount;

    public SimpleQueue(QueueProperties queueProperties, Stack<Double> randomNumbers, Events events) {
        this.queueProperties = queueProperties;
        this.randomNumbers = randomNumbers;
        this.events = events;

        var event = new Event(Action.IN, 2);
        events.add(event);

        statesTime = new double[queueProperties.getQueueCapacity() + 1];
    }

    public void receiveClient(double time) {
        //Contabiliza tempo??
        statesTime[clientsCount] += time - events.getLastExecuted();

        if (clientsCount < queueProperties.getQueueCapacity()) {
            // case para perda de cliente quand ofila cheia
            this.clientsCount++;

            if (clientsCount <= 1) {
                //agenda sainda
                scheduleExit(time);
            }
        }
        scheduleArrival(time);
    }

    public void serveClient(double time) {

        statesTime[clientsCount] += time - events.getLastExecuted();

        this.clientsCount--;

        if (clientsCount >= 1) {
            scheduleExit(time);
        }
    }

    public void printStatesTime() {
        StringBuilder msg = new StringBuilder();
        for (double v : statesTime) {
            msg.append("[").append(v).append("], ");
        }
        System.out.println(msg);
    }

    private void scheduleArrival(double time) {
        if (randomNumbers.isEmpty()) {
            return;
        }

        var randomNumber = randomNumbers.pop();
        var eventTime = (queueProperties.getArrivalEnd() - queueProperties.getArrivalStart()) * randomNumber + queueProperties.getArrivalStart() + time;
        var event = new Event(Action.IN, eventTime);
        events.add(event);
    }

    private void scheduleExit(double time) {
        if (randomNumbers.isEmpty()) {
            return;
        }
        var randomNumber = randomNumbers.pop();
        var eventTime = (queueProperties.getAttendanceEnd() - queueProperties.getAttendanceStart()) * randomNumber + queueProperties.getAttendanceStart() + time;
        var event = new Event(Action.OUT, eventTime);
        events.add(event);
    }
}



