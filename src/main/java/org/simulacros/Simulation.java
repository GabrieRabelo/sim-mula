package org.simulacros;

import org.simulacros.events.Action;
import org.simulacros.events.Scheduler;
import org.simulacros.generator.NumberGenerator;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.SimpleQueue;

import java.util.Arrays;
import java.util.Stack;

public class Simulation {

    private static final Stack<Double> randomNumbers = new Stack<>();

    public static void main(String[] args) {
        //3 2 4 3 5 5 1 100 45
        var arrivalStart = 2;
        var arrivalEnd = 4;
        var attendanceStart = 3;
        var attendanceEnd = 5;
        var queueCapacity = 5;
        var attendantsNumber = 1;
        var randomNumber = 100;
        var seedPseudRandomNumber = 45;

        var scheduler = new Scheduler(randomNumbers);

        var firstArrival = 3;

        var properties = QueueProperties.builder()
                .withArrivalInterval(new int[]{arrivalStart, arrivalEnd})
                .withAttendanceInterval(new int[]{attendanceStart, attendanceEnd})
                .withQueueCapacity(queueCapacity)
                .withAttendants(attendantsNumber)
                .build();
        startStack(randomNumber, seedPseudRandomNumber);

        var simpleQueue = new SimpleQueue(properties, scheduler, firstArrival);

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

    private static void startStack(int randomNumberLength, int seed) {
        NumberGenerator numberGenerator = new NumberGenerator(seed, 745, 9999, 800);
        for (int i = 0; i < randomNumberLength; i++) {
            randomNumbers.push(numberGenerator.nextDouble());
        }
    }
}