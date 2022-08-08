package io.trepix.ia.knapsack;

import java.util.Random;
import java.util.StringJoiner;

import static java.lang.Double.compare;

public class Solution implements io.trepix.ia.metaheuristics.Solution<Solution> {

    public final Knapsack knapsack;

    private final Random generator;

    public Solution(Knapsack knapsack, Random generator) {
        this.knapsack = knapsack;
        this.generator = generator;
    }

    private double weight() {
        return knapsack.weight();
    }
    @Override
    public double value() {
        return knapsack.value();
    }

    @Override
    public int compareTo(io.trepix.ia.metaheuristics.Solution<Solution> solution) {
        return compare(this.value(), solution.value());
    }

    @Override
    public boolean isBetterThan(Solution solution) {
        return this.compareTo(solution) > 0;
    }

    @Override
    public void improveWith(Solution betterSolution) {
        var item = betterSolution.knapsack.peekRandomItem(generator);

        if (knapsack.isNotCarrying(item)) {
            knapsack.add(item);
            while (knapsack.hasOverweight()) {
                knapsack.popRandomItem(generator);
            }
        }
    }

    public void randomFillWith(Items items) {
        knapsack.randomFillWith(items, generator);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" - ");
        sj.add("Valor : " + value());
        sj.add("Peso : " + weight());
        sj.add(knapsack.toString());
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution that = (Solution) o;
        return knapsack.equals(that.knapsack);
    }

    @Override
    public int hashCode() {
        return 0;
    }



}
