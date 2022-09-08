package org.simulacros.events;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private Action action;
    private double time;
    private boolean used;

    public Event(Action action, double time) {
        this.action = action;
        this.time = time;
        this.used = false;
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

    @Override
    public String toString() {
        return "Event{" +
                "action=" + action +
                ", time=" + time +
                '}';
    }
}
