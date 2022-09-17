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

        if(args.length == 0) {
            System.out.println("You are trying to run the program without parameters. \n" +
                    "In order to run Successfully the program, you need to provide the configuration with the following instructions:\n" +
                    "arg0: First arrival time\n" +
                    "arg1: Arrival start interval\n" +
                    "arg2: Arrival end interval\n" +
                    "arg3: Attendance start interval\n" +
                    "arg4: Attendance end interval\n" +
                    "arg5: Queue Capacity\n" +
                    "arg6: Attendants number\n" +
                    "arg7: Length of simulation random numbers\n" +
                    "arg8: Seed of pseud-random number generator.");
            return;
        }

        var scheduler = new Scheduler(randomNumbers);

        var firstArrival = Integer.parseInt(args[0]);

        var properties = QueueProperties.builder()
                .withArrivalInterval(new int[]{Integer.parseInt(args[1]), Integer.parseInt(args[2])})
                .withAttendanceInterval(new int[]{Integer.parseInt(args[3]),Integer.parseInt(args[4])})
                .withQueueCapacity(Integer.parseInt(args[5]))
                .withAttendants(Integer.parseInt(args[6]))
                .build();

        startStack(Integer.parseInt(args[7]), Integer.parseInt(args[8]));

        var simpleQueue = new SimpleQueue(properties, scheduler, firstArrival);

        while (!randomNumbers.isEmpty()){
            //get next event by time
            var event = scheduler.getNext();
            double time = event.getTime();

            if(event.getAction() == Action.IN){
                simpleQueue.receiveClient(time);
            }else{
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