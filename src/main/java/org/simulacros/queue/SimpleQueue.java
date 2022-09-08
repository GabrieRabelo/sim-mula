package org.simulacros.queue;

import org.simulacros.events.Action;
import org.simulacros.events.Event;
import org.simulacros.events.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SimpleQueue {

    private final QueueProperties queueProperties;
    private final Stack<Double> randomNumbers;

    //private final List<Double> states = new ArrayList<>();
    private final Events events;

    private int clientsCount;

    public SimpleQueue(QueueProperties queueProperties, Stack<Double> randomNumbers, Events events) {
        this.queueProperties = queueProperties;
        this.randomNumbers = randomNumbers;
        this.events = events;

        //var event = new Event(Action.IN, queueProperties.getArrivalStart());
        var event = new Event(Action.IN, 2);
        events.add(event);
    }

    public int getClientsCount() {
        return clientsCount;
    }

    public void receiveClient(double time) {
        //Contabiliza tempo??
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
        this.clientsCount--;
        if (clientsCount >= 1) {
            scheduleExit(time);
        }
    }

    private void scheduleArrival(double time) {
        if(randomNumbers.isEmpty())
            return;
        var randomNumber = randomNumbers.pop();
        var eventTime = (queueProperties.getArrivalEnd() - queueProperties.getArrivalStart()) * randomNumber + queueProperties.getArrivalStart() + time;
        var event = new Event(Action.IN, eventTime);
        events.add(event);
    }

    private void scheduleExit(double time) {
        if(randomNumbers.isEmpty())
            return;
        var eventTime = (queueProperties.getAttendanceEnd() - queueProperties.getAttendanceStart()) * randomNumbers.pop() + queueProperties.getAttendanceStart() + time;
        var event = new Event(Action.OUT, eventTime);
        events.add(event);
    }
}



