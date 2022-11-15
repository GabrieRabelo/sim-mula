package org.simulacros.queue;

import org.simulacros.input.QueueProperty;

import java.util.ArrayList;
import java.util.List;

public class Network {

    private List<Queue> queues;

    public Network(List<Queue> queues) {
        this.queues = queues;
    }

    private static Network fromProperty(List<QueueProperty> queueProperties) {
        var queues = new ArrayList<Queue>();

        for (QueueProperty property: queueProperties) {

            var kendallProps = property.getKendallNotation().split("/");

            var attendants = Integer.parseInt(kendallProps[2]);
            var capacity = kendallProps.length == 3 ? -1 : Integer.parseInt(kendallProps[3]);
            var attendanceInterval = property.getArrivalInterval().split("..");
            var attendanceStart = Double.parseDouble(attendanceInterval[0]);
            var attendanceEnd = Double.parseDouble(attendanceInterval[1]);

            var queuePropsBuilder = QueueProperties.builder()
                    .withAttendants(attendants)
                    .withQueueCapacity(capacity)
                    .withAttendanceInterval(new double[]{attendanceStart, attendanceEnd});

            if (property.getArrivalInterval() != null) {
                queuePropsBuilder.withArrivalInterval(new double[]{});
            }

            var queue = new Queue(queuePropsBuilder.build(),null);
            queues.add(queue);
        }

        return new Network(queues);
    }
}
