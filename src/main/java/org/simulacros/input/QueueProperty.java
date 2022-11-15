package org.simulacros.input;

import java.util.List;

public class QueueProperty {
    private int id;
    private String arrivalInterval;
    private String departureInterval;
    private String kendallNotation;
    private List<Route> routes;

    public QueueProperty() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArrivalInterval() {
        return arrivalInterval;
    }

    public void setArrivalInterval(String arrivalInterval) {
        this.arrivalInterval = arrivalInterval;
    }

    public String getDepartureInterval() {
        return departureInterval;
    }

    public void setDepartureInterval(String departureInterval) {
        this.departureInterval = departureInterval;
    }

    public String getKendallNotation() {
        return kendallNotation;
    }

    public void setKendallNotation(String kendallNotation) {
        this.kendallNotation = kendallNotation;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
