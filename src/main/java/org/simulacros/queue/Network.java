package org.simulacros.queue;

import org.simulacros.input.QueueProperty;

import java.util.ArrayList;
import java.util.List;

public class Network {

    private List<Queue> queues;

    public Network(List<Queue> queues) {
        this.queues = queues;
    }

    public Queue getById(int id) {
        for (Queue queue : queues) {
            if (queue.getQueueProperties().getQueueId() == id) return queue;
        }
        return null;
    }

    public void countAllTime(Double now) {
        for (Queue queue : queues) {
            queue.countTime(now);
        }
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public static Network fromProperty(List<QueueProperty> queueProperties) {
        var queues = new ArrayList<Queue>();

        for (QueueProperty property : queueProperties) {

            var kendallProps = property.getKendallNotation().split("/");

            var attendants = Integer.parseInt(kendallProps[2]);
            var capacity = kendallProps.length == 3 ? -1 : Integer.parseInt(kendallProps[3]);
            var attendanceInterval = property.getDepartureInterval().split("\\.\\.");
            var attendanceStart = Double.parseDouble(attendanceInterval[0]);
            var attendanceEnd = Double.parseDouble(attendanceInterval[1]);

            var queuePropsBuilder = QueueProperties.builder()
                    .withAttendants(attendants)
                    .withQueueCapacity(capacity)
                    .withRoutes(property.getRoutes())
                    .withQueueId(property.getId())
                    .withAttendanceInterval(new double[]{attendanceStart, attendanceEnd});
            if (property.getArrivalInterval() != null) {
                var arrivalInterval = property.getArrivalInterval().split("\\.\\.");
                var arrivalStart = Double.parseDouble(arrivalInterval[0]);
                var arrivalEnd = Double.parseDouble(arrivalInterval[1]);
                queuePropsBuilder.withArrivalInterval(new double[]{arrivalStart, arrivalEnd});
            }

            var queue = new Queue(queuePropsBuilder.build());
            queues.add(queue);
        }

        return new Network(queues);
    }
}
