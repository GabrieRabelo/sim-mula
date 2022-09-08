package org.simulacros;

import org.simulacros.events.Action;
import org.simulacros.events.Event;
import org.simulacros.events.Events;
import org.simulacros.generator.NumberGenerator;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.SimpleQueue;

import java.util.ArrayList;
import java.util.Stack;

public class Main {

    private static final Stack<Double> randomNumbers = new Stack<>();
    private static double time;

    public static void main(String[] args) {

        //var numberOfRandoms = 8;
        //startStack(numberOfRandoms);

        randomNumbers.push(0.9881);
        randomNumbers.push(0.7221);
        randomNumbers.push(0.6813);
        randomNumbers.push(0.5542);
        randomNumbers.push(0.1643);
        randomNumbers.push(0.8851);
        randomNumbers.push(0.3276);

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
            time = event.getTime();
            if(event.getAction() == Action.IN){
                simpleQueue.receiveClient(time);
            }else{
                simpleQueue.serveClient(time);
            }
            event.use();
        }
        System.out.println(events);

    }

    private static void startStack(int randomNumberLength) {
        NumberGenerator numberGenerator = new NumberGenerator(315, 745, 9999, 800);
        for (int i = 0; i < randomNumberLength; i++) {
            randomNumbers.push(numberGenerator.nextDouble());
        }
    }
}