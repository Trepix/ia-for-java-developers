package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Solution;

import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

import static java.lang.Double.compare;

// Una solución potencial = una carga posible de la mochila
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
        double pesos = 0.0;
        for (Item b : contenido) {
            pesos += b.weight();
        }
        return pesos;
    }
    @Override
    public double value() {
        double valor = 0.0;
        for (Item b : contenido) {
            valor += b.value;
        }
        return valor;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" - ");
        sj.add("Valor : " + value());
        sj.add("Peso : " + getPeso());
        for(Item b : contenido) {
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
