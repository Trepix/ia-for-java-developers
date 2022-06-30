package io.trepix.ia.algoritmogenetico;

import java.util.ArrayList;

import static java.util.stream.Collectors.joining;

public abstract class Individual {

    protected double fitness;
    protected ArrayList<Gene> genome;

    public double fitness() {
        return fitness;
    }

    public abstract void mutate();

    public abstract double evaluate();

    @Override
    public String toString() {
        String genomeStr = genome.stream()
                .map(Gene::toString)
                .collect(joining(" - "));
        return "(" + this.fitness + "): " + genomeStr;
    }
}
