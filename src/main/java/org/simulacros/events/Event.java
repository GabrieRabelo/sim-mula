package org.simulacros.events;


public class Event {

    private final Action action;
    private final double time;
    private boolean used;
    private Integer queueId;

    public Event(Action action, double time, Integer queueId) {
        this.action = action;
        this.time = time;
        this.used = false;
        this.queueId = queueId;
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


    public Integer getQueueId() {
        return this.queueId;
    } 

    @Override
    public String toString() {
        return "Event{" +
                "action=" + action +
                ", time=" + time +
                '}';
    }
}
