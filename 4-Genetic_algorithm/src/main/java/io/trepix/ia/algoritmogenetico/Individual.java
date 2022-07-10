package io.trepix.ia.algoritmogenetico;

import java.util.ArrayList;

import static java.util.stream.Collectors.joining;

public abstract class Individual {

    private Double fitness = null;
    protected ArrayList<Gene> genome;

    public double fitness() {
        if (fitness == null) {
            fitness = this.evaluate();
        }
        return fitness;
    }

    public abstract void mutate();

    protected abstract double evaluate();

    public boolean isBetterThan(Individual individual) {
        return this.fitness() <= individual.fitness();
    }

    @Override
    public String toString() {
        String genomeStr = genome.stream()
                .map(Gene::toString)
                .collect(joining(" - "));
        return "(" + this.fitness() + "): " + genomeStr;
    }
}
