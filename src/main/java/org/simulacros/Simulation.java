package org.simulacros;

import org.simulacros.events.Event;
import org.simulacros.events.Scheduler;
import org.simulacros.generator.NumberGenerator;
import org.simulacros.input.Properties;
import org.simulacros.queue.Network;
import org.simulacros.queue.Queue;

import java.io.IOException;
import java.util.Stack;

import static org.simulacros.events.Action.IN;

public class Simulation {

    public static void main(String[] args) throws IOException {

        var properties = Properties.fromJsonFile("properties");

        var randomNumbers = generateRandomStack(properties.getRandoms(), properties.getSeed());

        var scheduler = new Scheduler(randomNumbers);

        var network = Network.fromProperty(properties.getQueues());

        var firstArrival = new Event(IN, properties.getFirstArrival(), null, properties.getFirstQueue());
        scheduler.add(firstArrival);

        Queue firstQueue = null;
        Queue secondQueue = null;

        while (!randomNumbers.isEmpty()) {
            //get next event by time
            var event = scheduler.getNext();

            double now = event.getTime();

            // count time for each queue
            network.countAllTime(now);

            switch (event.getAction()) {
                case IN:
                    firstQueue = network.getById(event.getToQueueId());
                    firstQueue.receiveClient(now);
                    break;
                case PASSAGE:
                    firstQueue = network.getById(event.getFromQueueId());
                    secondQueue = network.getById(event.getToQueueId());
                    firstQueue.passClientOut(now);
                    secondQueue.passClientIn(now);
                    break;
                case OUT:
                    secondQueue = network.getById(event.getFromQueueId());
                    secondQueue.passClientOut(now);
                    break;
                default:
                    break;
            }
            event.use();
        }
        System.out.println(network.getQueues());
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