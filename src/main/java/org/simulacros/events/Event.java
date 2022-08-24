package org.simulacros.events;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private Action action;
    private double time;

    public Event(Action action, double time) {
        this.action = action;
        this.time = time;
    }
}
