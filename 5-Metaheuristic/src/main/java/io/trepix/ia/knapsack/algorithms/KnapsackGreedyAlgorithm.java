package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.knapsack.Caja;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.SolucionMochila;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.GreedyAlgorithm;

import java.util.ArrayList;
public class KnapsackGreedyAlgorithm extends GreedyAlgorithm<KnapsackProblem> {
    @Override
    protected Solution findSolution(Problem problem) {
        SolucionMochila solution = new SolucionMochila();
        KnapsackProblem pb = (KnapsackProblem) problem;
        ArrayList<Caja> items = pb.Cajas();
        items.sort((Caja b1, Caja b2) -> ((b2.valor / b2.pesos) >= (b1.valor / b1.pesos)) ? 1 : -1);
        double availableWeight = pb.pesosMax;
        for (Caja b : items) {
            if (b.pesos <= availableWeight) {
                solution.contenido.add(b);
                availableWeight -= b.pesos;
            }
        }
        return solution;
    }

    @Override
    protected Solution _findSolution(KnapsackProblem problem) {
        SolucionMochila solution = new SolucionMochila();
        KnapsackProblem pb = (KnapsackProblem) problem;
        ArrayList<Caja> items = pb.Cajas();
        items.sort((Caja b1, Caja b2) -> ((b2.valor / b2.pesos) >= (b1.valor / b1.pesos)) ? 1 : -1);
        double availableWeight = pb.pesosMax;
        for (Caja b : items) {
            if (b.pesos <= availableWeight) {
                solution.contenido.add(b);
                availableWeight -= b.pesos;
            }
        }
        return solution;
    }

}
