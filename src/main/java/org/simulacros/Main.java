package org.simulacros;

import org.simulacros.events.Action;
import org.simulacros.events.Events;
import org.simulacros.generator.NumberGenerator;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.SimpleQueue;

import java.util.Stack;

public class Main {

    private static final Stack<Double> randomNumbers = new Stack<>();

    public static void main(String[] args) {

        var numberOfRandoms = 10;
        startStack(numberOfRandoms);

        var events = new Events();

        var properties = QueueProperties.builder()
                .withArrivalStart(1)
                .withArrivalEnd(2)
                .withAttendanceStart(3)
                .withAttendanceEnd(6)
                .withQueueCapacity(3)
                .build();

        var simpleQueue = new SimpleQueue(properties, randomNumbers, events);
        while (!randomNumbers.isEmpty()){
            //get next event by time
            var event = events.getNext();
            double time = event.getTime();
            if(event.getAction() == Action.IN){
                simpleQueue.receiveClient(time);
            }else{
                simpleQueue.serveClient(time);
            }
            event.use();
        }

        System.out.println(events);
        simpleQueue.printStatesTime();
    }

    private static void startStack(int randomNumberLength) {
        NumberGenerator numberGenerator = new NumberGenerator(315, 745, 9999, 800);
        for (int i = 0; i < randomNumberLength; i++) {
            randomNumbers.push(numberGenerator.nextDouble());
        }
    }
}