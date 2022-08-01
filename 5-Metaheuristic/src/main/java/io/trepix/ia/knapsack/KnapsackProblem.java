package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.Solutions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class KnapsackProblem implements Problem {
    private final Random generator;
    private final double maxWeight;
    private final int neighboursNumber;
    protected List<Item> cajasDispo;

    public KnapsackProblem(List<Item> items, Configuration configuration) {
        this.maxWeight = configuration.maximumKnapsackWeight();
        this.cajasDispo = items;
        this.generator = configuration.random();
        this.neighboursNumber = configuration.neighboursNumber();
    }

    public List<Item> items() {
        return new ArrayList<>(cajasDispo);
    }

    public Items _items() {
        return new Items(new ArrayList<>(cajasDispo));
    }

    @Override
    public Solution randomSolution() {
        var knapsack = this.emptyKnapsack();
        Items items = _items();
        items.removeWhichCannotBeCarried(knapsack);
        while (knapsack.isNotFull() && items.isNotEmpty()) {
            Item item = items.popRandom(generator);
            knapsack.add(item);
            items.removeWhichCannotBeCarried(knapsack);
        }
        return new KnapsackSolution(knapsack);
    }

    public void EliminarDemasiadoPesadas(List<Item> cajasPosibles, double espacioDispo) {
        Iterator<Item> iterador = cajasPosibles.iterator();
        while (iterador.hasNext()) {
            Item b = iterador.next();
            if (b.weight() > espacioDispo) {
                iterador.remove();
            }
        }
    }

    @Override
    public Solutions neighbours(Solution solution) {
        ArrayList<Solution> neighbours = new ArrayList<>();
        var originalKnapsack = ((KnapsackSolution) solution).knapsack;

        for (int i = 0; i < neighboursNumber; i++) {
            var knapsack = originalKnapsack.clone();
            knapsack.popRandomItem(generator);
            var allItems = _items();
            allItems.removeUsedItems(knapsack);
            allItems.removeWhichCannotBeCarried(knapsack);

            while (knapsack.isNotFull() && allItems.isNotEmpty()) {
                Item item = allItems.popRandom(generator);
                knapsack.add(item);
                allItems.removeWhichCannotBeCarried(knapsack);
            }
            neighbours.add(new KnapsackSolution(knapsack));
        }
        return new Solutions(neighbours);
    }

    public Knapsack emptyKnapsack() {
        return new Knapsack(maximumWeight());
    }

    public double maximumWeight() {
        return maxWeight;
    }
}
