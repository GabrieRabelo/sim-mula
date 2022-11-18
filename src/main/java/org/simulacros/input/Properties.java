package org.simulacros.input;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Properties {
    private int randoms;
    private int seed;
    private List<QueueProperty> queues;

    private Double firstArrival;

    private int firstQueue;


    public Properties() {
    }

    public int getRandoms() {
        return randoms;
    }

    public int getSeed() {
        return seed;
    }

    public Double getFirstArrival() {
        return firstArrival;
    }

    public int getFirstQueue() {
        return firstQueue;
    }

    public List<QueueProperty> getQueues() {
        return queues;
    }

    public void setRandoms(int randoms) {
        this.randoms = randoms;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void setQueues(List<QueueProperty> queues) {
        this.queues = queues;
    }

    public static Properties fromJsonFile(String fileName) throws IOException {
        var objectMapper = new ObjectMapper();
        var path = String.format("src/main/resources/%s.json", fileName);
        return objectMapper.readValue(new File(path), Properties.class);
    }
}
