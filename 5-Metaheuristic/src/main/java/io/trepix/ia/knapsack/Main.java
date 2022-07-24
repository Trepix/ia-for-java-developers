package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.HumanMachineInterface;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.knapsack.algorithms.*;

import java.util.List;

public class Main implements HumanMachineInterface {
    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }

    @Override
    public void show(String message) {
        System.out.println(message);
    }

    private void run() {
        System.out.println("Optimization metaheuristics");
        ProblemaMochila pb = new ProblemaMochila();
        LanzarAlgoritmos(pb);
        System.out.println("*****************************************\n");
        pb = new ProblemaMochila(100, 30, 20);
        LanzarAlgoritmos(pb);
    }
    
    private void LanzarAlgoritmos(Problem problem) {
        knapsackAlgorithms().forEach(algorithm -> runAlgorithm(algorithm, problem));
    }

    private List<Algorithm> knapsackAlgorithms() {
        return List.of(
                new AlgorithmVorazMochila(),
                new DescensoGradienteMochila(),
                new BusquedaTabuMochila(),
                new RecocidoSimuladoMochila(),
                new EnjambreParticulasMochila()
        );
    }

    private void runAlgorithm(Algorithm algorithm, Problem problem) {
        System.out.println(algorithm.name());
        algorithm.Resolver(problem, this);
        System.out.println();
    }
}
