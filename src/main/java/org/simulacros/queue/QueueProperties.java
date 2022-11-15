package org.simulacros.queue;

public class QueueProperties {

    private final double[] arrivalInterval;
    private final double[] attendanceInterval;

    private final int queueCapacity;
    private final int attendants;

    private QueueProperties(Builder builder) {
        arrivalInterval = builder.arrivalInterval;
        attendanceInterval = builder.attendanceInterval;
        queueCapacity = builder.queueCapacity;
        attendants = builder.attendants;
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

    public static final class Builder {
        private double[] arrivalInterval;
        private double[] attendanceInterval;
        private int queueCapacity;
        private int attendants;

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

        public QueueProperties build() {
            return new QueueProperties(this);
        }
    }
}
