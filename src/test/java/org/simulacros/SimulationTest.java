package org.simulacros;

import org.junit.jupiter.api.Test;
import org.simulacros.events.Action;
import org.simulacros.events.Scheduler;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.SimpleQueue;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationTest {

    private static final Stack<Double> randomNumbers = new Stack<>();

    @Test
    void testSimulationUsingTeacherExample() {

        // Given
        randomNumbers.push(0.9881);
        randomNumbers.push(0.7221);
        randomNumbers.push(0.6813);
        randomNumbers.push(0.5542);
        randomNumbers.push(0.1643);
        randomNumbers.push(0.8851);
        randomNumbers.push(0.3276);

        var scheduler = new Scheduler(randomNumbers);

        var properties = QueueProperties.builder()
                .withArrivalInterval(new int[]{1,2})
                .withAttendanceInterval(new int[]{3,6})
                .withQueueCapacity(3)
                .withAttendants(1)
                .build();

        var firstArrival = 2;

        var simpleQueue = new SimpleQueue(properties, scheduler, firstArrival, 1);

        // When
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

        // Then
        assertEquals(2.0, simpleQueue.getStatesTime()[0]);
        assertEquals(1.8851, simpleQueue.getStatesTime()[1]);
        assertEquals(1.7851000000000004, simpleQueue.getStatesTime()[2]);
        assertEquals(2.655499999999999, simpleQueue.getStatesTime()[3]);

        assertEquals(24.02200415580672, simpleQueue.getProbabilities()[0]);
        assertEquals(22.641940017055624, simpleQueue.getProbabilities()[1]);
        assertEquals(21.44083980926529, simpleQueue.getProbabilities()[2]);
        assertEquals(31.895216017872364, simpleQueue.getProbabilities()[3]);
    }
}
