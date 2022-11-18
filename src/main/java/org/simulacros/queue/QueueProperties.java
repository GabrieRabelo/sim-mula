package org.simulacros.queue;

import org.simulacros.input.Route;

import java.util.List;

public class QueueProperties {

    private final double[] arrivalInterval;
    private final double[] attendanceInterval;

    private final int queueCapacity;
    private final int attendants;

    private List<Route> routes;

    private int queueId;

    private QueueProperties(Builder builder) {
        arrivalInterval = builder.arrivalInterval;
        attendanceInterval = builder.attendanceInterval;
        queueCapacity = builder.queueCapacity;
        attendants = builder.attendants;
        routes = builder.routes;
        queueId = builder.queueId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public double[] getArrivalInterval() {
        return arrivalInterval;
    }

    public double[] getAttendanceInterval() {
        return attendanceInterval;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public int getAttendants() {
        return attendants;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public int getQueueId() {
        return queueId;
    }

    public Integer getDestinationQueueId(Double randomNumber) {
        double sum = 0;
        for (Route route : routes) {
            var routeRate = route.getRate();
            sum += routeRate;
            if (sum > randomNumber) return route.getTo();
        }
        return 0;
    }

    public static final class Builder {
        private double[] arrivalInterval;
        private double[] attendanceInterval;
        private int queueCapacity;
        private int attendants;

        private List<Route> routes;

        private int queueId;

        private Builder() {
        }

        public Builder withArrivalInterval(double[] arrivalInterval) {
            this.arrivalInterval = arrivalInterval;
            return this;
        }

        public Builder withAttendanceInterval(double[] attendanceInterval) {
            this.attendanceInterval = attendanceInterval;
            return this;
        }

        public Builder withQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }

        public Builder withAttendants(int attendants) {
            this.attendants = attendants;
            return this;
        }

        public Builder withRoutes(List<Route> routes) {
            this.routes = routes;
            return this;
        }

        public Builder withQueueId(int queueId) {
            this.queueId = queueId;
            return this;
        }


        public QueueProperties build() {
            return new QueueProperties(this);
        }
    }
}
