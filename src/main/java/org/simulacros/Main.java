package org.simulacros;

import org.simulacros.generator.NumberGenerator;

import java.util.Stack;

public class Main {

    private static final Stack<Double> randomNumbers = new Stack<>();

    public static void main(String[] args) {

        var numberOfRandoms = 100000;
        startStack(numberOfRandoms);

        System.out.println(randomNumbers);


    }

    private static void startStack(int randomNumberLength) {
        NumberGenerator numberGenerator = new NumberGenerator(315, 745, 9999, 800);
        for (int i = 0; i < randomNumberLength; i++) {
            randomNumbers.push(numberGenerator.nextDouble());
        }
    }
}