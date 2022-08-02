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

    public double getPeso() {
        return knapsack().weight();
    }
    @Override
    public double value() {
        return knapsack().value();
    }

    @Override
    public void improveWith(KnapsackSolution betterSolution) {
        var betterKnapsack = betterSolution.knapsack();
        var item = betterKnapsack.peekRandomItem(generator);

        if (!knapsack.isCarrying(item)) {
            knapsack.add(item);
            while (knapsack.weight() + item.weight() > this.knapsack.maximumWeight()) {
                knapsack.popRandomItem(generator);
            }
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" - ");
        sj.add("Valor : " + value());
        sj.add("Peso : " + getPeso());
        for(Item b : knapsack().items()) {
            sj.add(b.toString());
        }
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KnapsackSolution solution)) {
            return false;
        }
        if (solution.knapsack.items().size() != this.knapsack.items().size() || solution.getPeso() != this.getPeso() || solution.value() != this.value()) {
            return false;
        }
        for(Item b : knapsack.items()) {
            if (!solution.knapsack.items().contains(b)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(knapsack);
    }

    @Override
    public int compareTo(Solution<KnapsackSolution> solution) {
        return compare(this.value(), solution.value());
    }

    @Override
    public boolean isBetterThan(KnapsackSolution solution) {
        return this.compareTo(solution) > 0;
    }

}
