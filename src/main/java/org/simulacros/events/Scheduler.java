package org.simulacros.events;

import org.simulacros.queue.QueueProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Scheduler {

    private final List<Event> events = new ArrayList<>();
    private final Stack<Double> randomNumbers;

    public Scheduler(Stack<Double> randomNumbers) {
        this.randomNumbers = randomNumbers;
    }

    public Event getNext() {
        return events.stream()
                .filter(Event::isNotUsed)
                .reduce((next, previous) -> (next.getTime() > previous.getTime()) ? previous : next)
                .orElse(null);
    }

    public Double getLastExecuted() {
        return events.stream()
                .filter(Event::isUsed)
                .reduce((next, previous) -> (next.getTime() > previous.getTime()) ? next : previous)
                .map(Event::getTime)
                .orElse(0D);
    }

    public void add(Event e) {
        this.events.add(e);
    }

    public void scheduleArrival(QueueProperties queueProperties, double time, int queueId) {
        if (randomNumbers.isEmpty()) {
            return;
        }

        // if (queueId == 0) {
            var start = queueProperties.getArrivalInterval()[0];
            var end = queueProperties.getArrivalInterval()[1];
            var eventTime = (end - start) * randomNumbers.pop() + start + time;
            var event = new Event(Action.IN, eventTime, queueId);
            this.add(event);
        // } else {
            // var eventTime = (end - start) * randomNumbers.pop() + start + time;
            // var event = new Event(Action.IN, time, queueId);
            // this.add(event);
        // }
    }

    public void scheduleExit(QueueProperties queueProperties, double time, int queueId) {
        if (randomNumbers.isEmpty()) {
            return;
        }
        var start = queueProperties.getAttendanceInterval()[0];
        var end = queueProperties.getAttendanceInterval()[1];
        var eventTime = (end - start) * randomNumbers.pop() + start + time;
        var event = new Event(Action.OUT, eventTime, queueId);
        this.add(event);
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "events=" + events +
                '}';
    }
}
