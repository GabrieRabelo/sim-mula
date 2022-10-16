package org.simulacros;

import org.simulacros.events.Action;
import org.simulacros.events.Scheduler;
import org.simulacros.generator.NumberGenerator;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.SimpleQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Simulation {

    private static final Stack<Double> randomNumbers = new Stack<>();

    public static void main(String[] args) {
        int queueQuantity = 2;
        var scheduler = new Scheduler(randomNumbers);

        var queues = new ArrayList<SimpleQueue>();
        var randomNumber = 100;
        var seedPseudRandomNumber = 45;

        var firstArrival = 3;
        var arrivalStart = 2;
        var arrivalEnd = 4;
        var attendanceStart = 3;
        var attendanceEnd = 5;
        var queueCapacity = 5;
        var attendantsNumber = 1;

        var properties = QueueProperties.builder()
                .withArrivalInterval(new int[] { arrivalStart, arrivalEnd })
                .withAttendanceInterval(new int[] { attendanceStart, attendanceEnd })
                .withQueueCapacity(queueCapacity)
                .withAttendants(attendantsNumber)
                .build();
        startStack(randomNumber, seedPseudRandomNumber);
        queues.add(new SimpleQueue(properties, scheduler, firstArrival, 0));

        for (int i = 1; i < queueQuantity; i++) {
            properties = QueueProperties.builder()
                    .withArrivalInterval(new int[] { arrivalStart, arrivalEnd })
                    .withAttendanceInterval(new int[] { attendanceStart, attendanceEnd })
                    .withQueueCapacity(queueCapacity)
                    .withAttendants(attendantsNumber)
                    .build();
            startStack(randomNumber, seedPseudRandomNumber);
            queues.add(new SimpleQueue(properties, scheduler, 3, i));
        }

        while (!randomNumbers.isEmpty()) {
            // get next event by time
            var event = scheduler.getNext();
            double time = event.getTime();
            int queueId = event.getQueueId();

            if (event.getAction() == Action.IN) {
                queues.get(queueId).receiveClient(time);
            } else if (event.getAction() == Action.OUT) {
                queues.get(queueId).serveClient(time);
                if (queueId != queues.size() - 1) {
                    queues.get(queueId + 1).receiveClient(time);
                }
            }

            event.use();
        }

        System.out.println("States: " + Arrays.toString(queues.get(0).getStatesTime()));
        System.out.println("---------------");
        System.out.println("Probabilities:" + Arrays.toString(queues.get(0).getProbabilities()));
        System.out.println();
        System.out.println("Lost Clients: " + queues.get(0).getLostClients());
    }

    private static void startStack(int randomNumberLength, int seed) {
        NumberGenerator numberGenerator = new NumberGenerator(seed, 745, 9999, 800);
        for (int i = 0; i < randomNumberLength; i++) {
            randomNumbers.push(numberGenerator.nextDouble());
        }
    }
}