package org.simulacros;

import org.junit.jupiter.api.Test;
import org.simulacros.events.Action;
import org.simulacros.events.Event;
import org.simulacros.events.Scheduler;
import org.simulacros.queue.QueueProperties;
import org.simulacros.queue.Queue;

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
                .withArrivalInterval(new double[]{1,2})
                .withAttendanceInterval(new double[]{3,6})
                .withQueueCapacity(3)
                .withAttendants(1)
                .build();

        var queue = new Queue(properties, scheduler);

        var firstArrivalEvent = new Event(Action.IN, 2.0);
        scheduler.add(firstArrivalEvent);

        // When
        while (!randomNumbers.isEmpty()){
            //get next event by time
            var event = scheduler.getNext();
            double time = event.getTime();

            queue.countTime(time);

            if(event.getAction() == Action.IN){
                queue.receiveClient(time);
            }else{
                queue.serveClient(time);
            }

            event.use();
        }

        // Then
        assertEquals(2.0, queue.getStatesTime()[0]);
        assertEquals(1.8851, queue.getStatesTime()[1]);
        assertEquals(1.7851000000000004, queue.getStatesTime()[2]);
        assertEquals(2.655499999999999, queue.getStatesTime()[3]);

        assertEquals(24.02200415580672, queue.getProbabilities()[0]);
        assertEquals(22.641940017055624, queue.getProbabilities()[1]);
        assertEquals(21.44083980926529, queue.getProbabilities()[2]);
        assertEquals(31.895216017872364, queue.getProbabilities()[3]);
    }

    @Test
    void testSimulationUsingTeacherExample_tandem() {
        var randomNumbers = new Stack<Double>();
        randomNumbers.push(0.2921);
        randomNumbers.push(0.6001);
        randomNumbers.push(0.8545);
        randomNumbers.push(0.2299);
        randomNumbers.push(0.0171);
        randomNumbers.push(0.9208);
        randomNumbers.push(0.5121);
        randomNumbers.push(0.4569);
        randomNumbers.push(0.0132);
        randomNumbers.push(0.9023);
        randomNumbers.push(0.8963);
        randomNumbers.push(0.3398);
        randomNumbers.push(0.2761);
        randomNumbers.push(0.5534);
        randomNumbers.push(0.0004);
        randomNumbers.push(0.9921);

        var scheduler = new Scheduler(randomNumbers);

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

        assertEquals(2.6647999999999996, firstQueue.getStatesTime()[0]);
        assertEquals(7.7764, firstQueue.getStatesTime()[1]);
        assertEquals(6.207500000000002, firstQueue.getStatesTime()[2]);
        assertEquals(0.6997999999999998, firstQueue.getStatesTime()[3]);

        assertEquals(15.360405798772225, firstQueue.getProbabilities()[0]);
        assertEquals(44.82462460731475, firstQueue.getProbabilities()[1]);
        assertEquals(35.78119145747472, firstQueue.getProbabilities()[2]);
        assertEquals(4.033778136438307, firstQueue.getProbabilities()[3]);

        assertEquals(7.4763, secondQueue.getStatesTime()[0]);
        assertEquals(0.6843000000000004, secondQueue.getStatesTime()[1]);
        assertEquals(7.692500000000004, secondQueue.getStatesTime()[2]);
        assertEquals(1.4953999999999965, secondQueue.getStatesTime()[3]);

        assertEquals(43.09479205695016, secondQueue.getProbabilities()[0]);
        assertEquals(3.944433236302852, secondQueue.getProbabilities()[1]);
        assertEquals(44.341009309162196, secondQueue.getProbabilities()[2]);
        assertEquals(8.619765397584784, secondQueue.getProbabilities()[3]);
    }
}
