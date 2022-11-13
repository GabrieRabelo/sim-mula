package org.simulacros.input;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Properties {
    private int randoms;
    private int seed;
    private List<Queue> queues;

    public Properties() {
    }

    public int getRandoms() {
        return randoms;
    }

    public int getSeed() {
        return seed;
    }

    public List<Queue> getQueues() {
        return queues;
    }

    public void setRandoms(int randoms) {
        this.randoms = randoms;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }

    public static Properties fromJsonFile(String fileName) throws IOException {
        var objectMapper = new ObjectMapper();
        var path = String.format("src/main/resources/%s.json", fileName);
        return objectMapper.readValue(new File(path), Properties.class);
    }

    public static void main(String[] args) throws IOException {
        fromJsonFile("properties");
    }
}
