package io.trepix.ia;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.algorithms.*;
import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.HumanMachineInterface;
import io.trepix.ia.metaheuristics.Problem;

import java.util.List;

import static io.trepix.ia.knapsack.KnapsackProblemBuilder.ItemBuilder.name;
import static io.trepix.ia.knapsack.KnapsackProblemBuilder.withItems;
import static io.trepix.ia.knapsack.KnapsackProblemBuilder.withRandomItems;

public class Main implements HumanMachineInterface {

    private static final Main app = new Main();
    public static void main(String[] args) {
        System.out.println("Optimization metaheuristics");
        Problem problem = defaultItems();
        runAlgorithms(problem);

        System.out.println("*****************************************\n");
        problem = withRandomItems()
                .withNumberOfItems(100)
                .maxItemValue(20)
                .maxKnapsackWeight(30)
                .build();
        runAlgorithms(problem);
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

    private static void runAlgorithms(Problem problem) {
        knapsackAlgorithms().forEach(algorithm -> runAlgorithm(algorithm, problem));
    }
    private static List<Algorithm> knapsackAlgorithms() {
        return List.of(
                new KnapsackGreedyAlgorithm(app),
                new KnapsackGradientDescent(app),
                new KnapsackTabuSearch(app),
                new KnapsackParticleSwarm(app),
                new KnapsackSimulatedAnnealing(app)
        );
    }

    private static void runAlgorithm(Algorithm algorithm, Problem problem) {
        System.out.println(algorithm.name());
        algorithm.solve(problem);
        System.out.println();
    }

    @Override
    public void show(String message) {
        System.out.println(message);
    }
}
