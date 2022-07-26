package io.trepix.ia.knapsack.algorithms;

import io.trepix.ia.metaheuristics.HumanMachineInterface;
import io.trepix.ia.metaheuristics.Solution;
import io.trepix.ia.metaheuristics.algorithms.AlgorithmVoraz;
import io.trepix.ia.knapsack.Caja;
import io.trepix.ia.knapsack.KnapsackProblem;
import io.trepix.ia.knapsack.SolucionMochila;
import java.util.ArrayList;
import java.util.Collections;

// Algoritmo voraz para el problema de la mochila
public class KnapsackGreedyAlgorithm extends AlgorithmVoraz {
    SolucionMochila solucion;

    private final HumanMachineInterface hmi;

    public KnapsackGreedyAlgorithm(HumanMachineInterface hmi) {
        this.hmi = hmi;
    }

    @Override
    protected Solution ConstruirSolucion() {
        solucion = new SolucionMochila();
        KnapsackProblem pb = (KnapsackProblem) problem;
        ArrayList<Caja> cajasPosibles = pb.Cajas();
        Collections.sort(cajasPosibles, (Caja b1, Caja b2) -> (int) (((b2.valor/b2.pesos) >= (b1.valor/b1.pesos)) ? 1 : -1));
        double espacioDispo = pb.pesosMax;
        for (Caja b : cajasPosibles) {
            if (b.pesos <= espacioDispo) {
                solucion.contenido.add(b);
                espacioDispo -= b.pesos;
            }
        }
        return solucion;
    }

}
