package io.trepix.ia;

import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.HumanMachineInterface;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.knapsack.algorithms.*;

import java.util.List;

public class Main implements HumanMachineInterface {

    private static final Main app = new Main();

    private static final List<Algorithm> KNAPSACK_ALGORITHMS = List.of(
            new KnapsackGreedyAlgorithm(app),
            new KnapsackGradientDescent(app),
            new KnapsackTabuSearch(app),
            new KnapsackSimulatedAnnealing(app),
            new KnapsackParticleSwarm(app)
    );
    public static void main(String[] args) {
        app.run();
    }

    @Override
    public void show(String message) {
        System.out.println(message);
    }

    private void run() {
        System.out.println("Optimization metaheuristics");
        Problem problem = new KnapsackProblem();
        runAlgorithms(problem);
        
        System.out.println("*****************************************\n");
        problem = new KnapsackProblem(100, 30, 20);
        runAlgorithms(problem);
    }
    
    private void runAlgorithms(Problem problem) {
        KNAPSACK_ALGORITHMS.forEach(algorithm -> runAlgorithm(algorithm, problem));
    }

    private void runAlgorithm(Algorithm algorithm, Problem problem) {
        System.out.println(algorithm.name());
        algorithm.solve(problem);
        System.out.println();
    }
}
