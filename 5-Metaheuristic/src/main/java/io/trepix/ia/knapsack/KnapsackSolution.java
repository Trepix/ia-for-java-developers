package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Solution;

import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

import static java.lang.Double.compare;

public class KnapsackSolution implements Solution<KnapsackSolution> {

    public final Knapsack knapsack;

    private final Random generator;
    public List<Item> contenido;

    public KnapsackSolution(Knapsack knapsack, Random generator) {
        contenido = knapsack.items();
        this.knapsack = knapsack;
        this.generator = generator;
    }

    public Knapsack knapsack() {
        var knapsack = new Knapsack(this.knapsack.maximumWeight());
        for (Item item : contenido) {
            knapsack.add(item);
        }
        return knapsack;
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
        int index = generator.nextInt(betterSolution.contenido.size());
        Item item = betterSolution.contenido.get(index);
        if (!this.contenido.contains(item)) {
            this.contenido.add(item);
            while (this.getPeso() + item.weight() > this.knapsack.maximumWeight()) {
                index = generator.nextInt(this.contenido.size());
                this.contenido.remove(index);
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
        if (!(o instanceof KnapsackSolution)) {
            return false;
        }
        KnapsackSolution solution = (KnapsackSolution) o;
        if (solution.contenido.size() != this.contenido.size() || solution.getPeso() != this.getPeso() || solution.value() != this.value()) {
            return false;
        }
        for(Item b : contenido) {
            if (!solution.contenido.contains(b)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 42;
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
