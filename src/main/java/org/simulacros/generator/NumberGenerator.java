package org.simulacros.generator;

import java.util.Locale;

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

    public static void main(String[] args) {
        var generator = new NumberGenerator(315, 745, 9999, 800);
        for (int i = 0; i < 1000; i++) {
            System.out.println(String.format(
                    Locale.FRANCE, "%,.2f", generator.nextDouble()));
        }
    }
}
