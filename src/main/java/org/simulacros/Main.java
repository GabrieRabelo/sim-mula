package org.simulacros;

import org.simulacros.events.Event;
import org.simulacros.generator.NumberGenerator;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.SimpleQueue;

import java.util.ArrayList;
import java.util.Stack;

public class Main {

    private static final Stack<Double> randomNumbers = new Stack<>();

    public static void main(String[] args) {

        var numberOfRandoms = 100000;
        startStack(numberOfRandoms);

        var events = new ArrayList<Event>();

        var properties = QueueProperties.builder()
                .withArrivalStart(2)
                .withArrivalEnd(4)
                .withAttendanceStart(5)
                .withAttendanceEnd(7)
                .withQueueCapacity(4)
                .build();

        var simpleQueue = new SimpleQueue(properties, randomNumbers, events);

    }

    private static void startStack(int randomNumberLength) {
        NumberGenerator numberGenerator = new NumberGenerator(315, 745, 9999, 800);
        for (int i = 0; i < randomNumberLength; i++) {
            randomNumbers.push(numberGenerator.nextDouble());
        }
    }
}