package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.HumanMachineInterface;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.knapsack.algorithms.*;

import java.util.List;

public class Main implements HumanMachineInterface {

    private static final Main app = new Main();

    private static final List<Algorithm> ALGORITHMS = List.of(
            new AlgorithmVorazMochila(app),
            new DescensoGradienteMochila(app),
            new BusquedaTabuMochila(app),
            new RecocidoSimuladoMochila(app),
            new EnjambreParticulasMochila(app)
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
        ProblemaMochila pb = new ProblemaMochila();
        LanzarAlgoritmos(pb);
        System.out.println("*****************************************\n");
        pb = new ProblemaMochila(100, 30, 20);
        LanzarAlgoritmos(pb);
    }
    
    private void LanzarAlgoritmos(Problem problem) {
        ALGORITHMS.forEach(algorithm -> runAlgorithm(algorithm, problem));
    }

    private void runAlgorithm(Algorithm algorithm, Problem problem) {
        System.out.println(algorithm.name());
        algorithm.solve(problem);
        System.out.println();
    }
}
