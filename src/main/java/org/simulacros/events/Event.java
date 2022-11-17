package org.simulacros.events;


public class Event {

    private final Action action;
    private final double time;
    private boolean used;

    private Integer fromQueueId, toQueueId;

    public Event(Action action, double time, Integer fromQueueId, Integer toQueueId) {
        this.action = action;
        this.time = time;
        this.used = false;
        this.fromQueueId = fromQueueId;
        this.toQueueId = toQueueId;
    }

    public Action getAction() {
        return action;
    }

    public double getTime() {
        return time;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isNotUsed() {
        return !used;
    }

    public void use() {
        this.used = true;
    }

    public Integer getFromQueueId() {
        return fromQueueId;
    }

    public Integer getToQueueId() {
        return toQueueId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "action=" + action +
                ", time=" + time +
                '}';
    }
}
