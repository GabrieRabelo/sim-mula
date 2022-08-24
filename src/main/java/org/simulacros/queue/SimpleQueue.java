package org.simulacros.queue;

import org.simulacros.events.Action;
import org.simulacros.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SimpleQueue {

    private double time;
    private final QueueProperties queueProperties;
    private final Stack<Double> randomNumbers;

    private final List<Double> states = new ArrayList<>();
    private final List<Event> events;

    private int clientsCount;

    public SimpleQueue(QueueProperties queueProperties, Stack<Double> randomNumbers, List<Event> events) {
        this.queueProperties = queueProperties;
        this.randomNumbers = randomNumbers;
        this.events = events;

        var event = new Event(Action.IN, queueProperties.getArrivalStart());
        events.add(event);
    }

    public double getTime() {
        return time;
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

    public void serveClient() {
        this.clientsCount--;
        if (clientsCount >= 1) {
            schdeuleExit();
        }
    }

    private void scheduleArrival() {
        var eventTime = queueProperties.getArrivalEnd() - queueProperties.getArrivalStart() * randomNumbers.pop() + queueProperties.getArrivalStart() + time;
        var event = new Event(Action.IN, eventTime);
        events.add(event);
    }

    private void schdeuleExit() {
        var eventTime = queueProperties.getAttendanceEnd() - queueProperties.getAttendanceStart() * randomNumbers.pop() + queueProperties.getAttendanceStart() + time;
        var event = new Event(Action.OUT, eventTime);
        events.add(event);
    }
}



