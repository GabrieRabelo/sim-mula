package org.simulacros;

import org.simulacros.events.Action;
import org.simulacros.events.Scheduler;
import org.simulacros.generator.NumberGenerator;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.SimpleQueue;

import java.util.Arrays;
import java.util.Stack;

public class Simulation {

    public static void main(String[] args) {

        // first
        var randomNumber = 100000;
        var seedPseudRandomNumber = 45;

        var properties = QueueProperties.builder()
                .withArrivalInterval(new int[]{2, 4})
                .withAttendanceInterval(new int[]{3, 5})
                .withQueueCapacity(5)
                .withAttendants(1)
                .withFirstArrival(3)
                .build();

        var randomNumbers = generateRandomStack(randomNumber, seedPseudRandomNumber);

        var scheduler = new Scheduler(randomNumbers);

        var simpleQueue = new SimpleQueue(properties, scheduler);

        while (!randomNumbers.isEmpty()) {
            //get next event by time
            var event = scheduler.getNext();
            double time = event.getTime();

            if (event.getAction() == Action.IN) {
                simpleQueue.receiveClient(time);
            } else {
                simpleQueue.serveClient(time);
            }

            event.use();
        }

        System.out.println("States: " + Arrays.toString(simpleQueue.getStatesTime()));
        System.out.println("---------------");
        System.out.println("Probabilities:" + Arrays.toString(simpleQueue.getProbabilities()));
        System.out.println();
        System.out.println("Lost Clients: " + simpleQueue.getLostClients());
    }

    private static Stack<Double> generateRandomStack(int randomNumberLength, int seed) {
        NumberGenerator numberGenerator = new NumberGenerator(seed, 745, 9999, 800);
        final Stack<Double> randomNumbers = new Stack<>();
        for (int i = 0; i < randomNumberLength; i++) {
            randomNumbers.push(numberGenerator.nextDouble());
        }
        return randomNumbers;
    }
}