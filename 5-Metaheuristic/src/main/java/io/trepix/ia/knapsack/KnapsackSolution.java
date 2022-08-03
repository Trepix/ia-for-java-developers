package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Solution;

import java.util.Objects;
import java.util.Random;
import java.util.StringJoiner;

import static java.lang.Double.compare;

public class KnapsackSolution implements Solution<KnapsackSolution> {

    public final Knapsack knapsack;

    private final Random generator;

    public KnapsackSolution(Knapsack knapsack, Random generator) {
        this.knapsack = knapsack;
        this.generator = generator;
    }

    public Knapsack knapsack() {
        return knapsack.clone();
    }

    private double weight() {
        return knapsack().weight();
    }
    @Override
    public double value() {
        return knapsack().value();
    }

    @Override
    public int compareTo(Solution<KnapsackSolution> solution) {
        return compare(this.value(), solution.value());
    }

    @Override
    public boolean isBetterThan(KnapsackSolution solution) {
        return this.compareTo(solution) > 0;
    }

    @Override
    public void improveWith(KnapsackSolution betterSolution) {
        var betterKnapsack = betterSolution.knapsack();
        var item = betterKnapsack.peekRandomItem(generator);

        if (knapsack.isNotCarrying(item)) {
            knapsack.add(item);
            while (knapsack.hasOverweight()) {
                knapsack.popRandomItem(generator);
            }
        }
    }

    @Override
    public String toString() { // TODO: make knapsack iterable of items
        StringJoiner sj = new StringJoiner(" - ");
        sj.add("Valor : " + value());
        sj.add("Peso : " + weight());
        for(Item b : knapsack().items()) {
            sj.add(b.toString());
        }
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnapsackSolution that = (KnapsackSolution) o;
        return knapsack.equals(that.knapsack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(knapsack);
    }



}
