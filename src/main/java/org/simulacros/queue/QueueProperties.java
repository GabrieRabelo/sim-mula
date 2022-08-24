package org.simulacros.queue;

public class QueueProperties {

    private final int arrivalStart;
    private final int arrivalEnd;
    private final int attendanceStart;
    private final int attendanceEnd;
    private final int queueCapacity;

    private QueueProperties(Builder builder) {
        arrivalStart = builder.arrivalStart;
        arrivalEnd = builder.arrivalEnd;
        attendanceStart = builder.attendanceStart;
        attendanceEnd = builder.attendanceEnd;
        queueCapacity = builder.queueCapacity;
    }

    public int getArrivalStart() {
        return arrivalStart;
    }

    public int getArrivalEnd() {
        return arrivalEnd;
    }

    public int getAttendanceStart() {
        return attendanceStart;
    }

    public int getAttendanceEnd() {
        return attendanceEnd;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int arrivalStart;
        private int arrivalEnd;
        private int attendanceStart;
        private int attendanceEnd;
        private int queueCapacity;

        private Builder() {
        }

        public Builder withArrivalStart(int arrivalStart) {
            this.arrivalStart = arrivalStart;
            return this;
        }

        public Builder withArrivalEnd(int arrivalEnd) {
            this.arrivalEnd = arrivalEnd;
            return this;
        }

        public Builder withAttendanceStart(int attendanceStart) {
            this.attendanceStart = attendanceStart;
            return this;
        }

        public Builder withAttendanceEnd(int attendanceEnd) {
            this.attendanceEnd = attendanceEnd;
            return this;
        }

        public Builder withQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }

        public QueueProperties build() {
            return new QueueProperties(this);
        }
    }
}
