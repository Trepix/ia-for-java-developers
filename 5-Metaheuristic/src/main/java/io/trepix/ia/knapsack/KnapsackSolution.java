package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Solution;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

// Una solución potencial = una carga posible de la mochila
public class KnapsackSolution implements Solution {
    public List<Item> contenido;
    
    public KnapsackSolution() {
        contenido = new ArrayList();
    }

    public KnapsackSolution(Knapsack knapsack) {
        contenido = knapsack.items();
    }
    
    public KnapsackSolution(KnapsackSolution original) {
        contenido = new ArrayList();
        contenido.addAll(original.contenido);
    }
    
    public double getPeso() {
        double pesos = 0.0;
        for (Item b : contenido) {
            pesos += b.weight();
        }
        return pesos;
    }

    @Override
    public boolean isBetterThan(Solution solution) {
        return this.value() > solution.value();
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
        KnapsackSolution sol = (KnapsackSolution) o;
        if (sol.contenido.size() != this.contenido.size() || sol.getPeso() != this.getPeso() || sol.value() != this.value()) {
            return false;
        }
        for(Item b : contenido) {
            if (!sol.contenido.contains(b)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 42;
    }
}
