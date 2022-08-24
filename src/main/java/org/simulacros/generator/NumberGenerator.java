package org.simulacros.generator;

public class NumberGenerator {

    private double current;
    private final double a;
    private final double c;
    private final double M;

    public NumberGenerator(double seed, double a, double M, double c) {
        this.current = seed;
        this.a = a;
        this.M = M;
        this.c = c;
    }

    public double nextDouble() {
        this.current = (a * this.current + c) % M;
        return this.current / M;
    }
}
