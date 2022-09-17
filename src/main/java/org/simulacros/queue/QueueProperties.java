package org.simulacros.queue;

public class QueueProperties {

    private final int[] arrivalInterval;
    private final int[] attendanceInterval;

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

    public int[] getArrivalInterval() {
        return arrivalInterval;
    }

    public int[] getAttendanceInterval() {
        return attendanceInterval;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public int getAttendants() {
        return attendants;
    }

    public static final class Builder {
        private int[] arrivalInterval;
        private int[] attendanceInterval;
        private int queueCapacity;
        private int attendants;

        private Builder() {
        }

        public Builder withArrivalInterval(int[] arrivalInterval) {
            this.arrivalInterval = arrivalInterval;
            return this;
        }

        public Builder withAttendanceInterval(int[] attendanceInterval) {
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
