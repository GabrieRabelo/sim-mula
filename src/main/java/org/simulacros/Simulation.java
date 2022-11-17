package org.simulacros;

import org.simulacros.events.Action;
import org.simulacros.events.Event;
import org.simulacros.events.Scheduler;
import org.simulacros.generator.NumberGenerator;
import org.simulacros.input.Properties;
import org.simulacros.queue.Network;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.Queue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class Simulation {

    public static void main(String[] args) throws IOException {

        var properties = Properties.fromJsonFile("properties");

        var randomNumbers = generateRandomStack(properties.getRandoms(), properties.getSeed());

        var scheduler = new Scheduler(randomNumbers);

        var network = Network.fromProperty(properties.getQueues());

        // Creating queues
        var firstQueueProperties = QueueProperties.builder()
                .withArrivalInterval(new double[]{2, 3})
                .withAttendanceInterval(new double[]{2, 5})
                .withAttendants(2)
                .withQueueCapacity(3)
                .build();

        var secondQueueProperties = QueueProperties.builder()
                .withAttendanceInterval(new double[]{3, 5})
                .withAttendants(1)
                .withQueueCapacity(3)
                .build();

        var firstQueue = new Queue(firstQueueProperties, scheduler);
        var secondQueue = new Queue(secondQueueProperties, scheduler);

        // adds first arrival event
        var firstArrival = new Event(Action.IN, 2.5);
        scheduler.add(firstArrival);

        while (!randomNumbers.isEmpty()) {
            //get next event by time
            var event = scheduler.getNext();

            double now = event.getTime();

            firstQueue.countTime(now);
            secondQueue.countTime(now);



            switch (event.getAction()) {
                case IN:
                    firstQueue.receiveClient(now);
                    break;
                case PASSAGE:
                    firstQueue.passClientOut(now);
                    secondQueue.passClientIn(now);
                    break;
                case OUT:
                    secondQueue.serveClient(now);
                    break;
                default:
                    break;
            }

            event.use();
        }

        System.out.println("First States: " + Arrays.toString(firstQueue.getStatesTime()));
        System.out.println("First Probabilities:" + Arrays.toString(firstQueue.getProbabilities()));
        System.out.println("First Lost Clients: " + firstQueue.getLostClients());
        System.out.println("----------");
        System.out.println("Second States: " + Arrays.toString(secondQueue.getStatesTime()));
        System.out.println("Second Probabilities:" + Arrays.toString(secondQueue.getProbabilities()));
        System.out.println("Second Lost Clients: " + secondQueue.getLostClients());
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