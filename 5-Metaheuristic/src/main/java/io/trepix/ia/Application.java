package io.trepix.ia;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.KnapsackProblemBuilder;
import io.trepix.ia.knapsack.algorithms.*;
import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Solution;

import java.util.List;

import static io.trepix.ia.knapsack.KnapsackProblemBuilder.ItemBuilder.name;
import static io.trepix.ia.knapsack.KnapsackProblemBuilder.withItems;
import static io.trepix.ia.knapsack.KnapsackProblemBuilder.withRandomItems;
import static java.lang.Long.parseLong;

public class Application {
    private static final List<Algorithm<KnapsackProblem>> KNAPSACK_ALGORITHMS = List.of(
            new KnapsackGreedyAlgorithm(),
            new KnapsackGradientDescent(),
            new KnapsackTabuSearch(),
            new KnapsackSimulatedAnnealing(),
            new KnapsackParticleSwarm()
    );

    public static void main(String[] args) {

        System.out.println("Optimization metaheuristics");
        var problem = defaultItems();
        runAlgorithms(problem);

        System.out.println("*****************************************\n");
        problem = knapsackProblemBuilder(args)
                .withNumberOfItems(100)
                .maxItemValue(20)
                .maxKnapsackWeight(30)
                .build();
        runAlgorithms(problem);
    }

    private static KnapsackProblemBuilder knapsackProblemBuilder(String[] args) {
        if (args.length < 1) return withRandomItems();
        long seed = parseLong(args[0]);
        return withRandomItems(seed);
    }

    private static KnapsackProblem defaultItems() {
        return withItems(
                name("A").weight(4).value(15),
                name("B").weight(7).value(15),
                name("C").weight(10).value(20),
                name("D").weight(3).value(10),
                name("E").weight(6).value(11),
                name("F").weight(12).value(16),
                name("G").weight(11).value(12),
                name("H").weight(16).value(22),
                name("I").weight(5).value(12),
                name("J").weight(14).value(21),
                name("K").weight(4).value(10),
                name("L").weight(3).value(7))
                .maxKnapsackWeight(20)
                .build();
    }

    private static void runAlgorithms(KnapsackProblem problem) {
        KNAPSACK_ALGORITHMS.forEach(algorithm -> runAlgorithm(algorithm, problem));
    }

    private static void runAlgorithm(Algorithm<KnapsackProblem> algorithm, KnapsackProblem problem) {
        System.out.println(algorithm.name());
        Solution solution = algorithm.solve(problem);
        System.out.println(solution.toString());
        System.out.println();
    }

}
