package org.simulacros.events;

import java.util.ArrayList;
import java.util.List;

public class Events {
    private List<Event> events = new ArrayList<>();

    public Event getNext(){
        return events.stream()
                .filter(Event::isNotUsed)
                .reduce((next, previous) -> (next.getTime() > previous.getTime()) ? previous : next)
                .get();
    }

    public void add(Event e){
        this.events.add(e);
    }

    @Override
    public String toString() {
        return "Events{" +
                "events=" + events +
                '}';
    }
}
