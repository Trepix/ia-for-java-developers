package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.List;

public abstract class SimulatedAnnealing<T extends Problem> extends Algorithm<T> {

    protected Problem problem;
    protected Solution solucionActual;
    protected Solution mejorSolucion;
    protected double temperatura;

    public SimulatedAnnealing() {
        super("Simulated Annealing");
    }

    @Override
    public final Solution solve(T problem) {
        this.problem = problem;

        solucionActual = this.problem.randomSolution();
        mejorSolucion = solucionActual;
        InitializarTemperatura();

        while (!CriterioParada()) {
            List<Solution> vecindario = this.problem.neighbours(solucionActual);
            if (vecindario != null) {
                Solution mejorVecino = this.problem.MejorSolucion(vecindario);
                Actualizar(mejorVecino);
            }
            Incrementar();
            ActualizarTemperatura();
        }
        return mejorSolucion;
    }

    protected abstract void ActualizarTemperatura();

    protected abstract void InitializarTemperatura();

    protected abstract boolean CriterioParada();

    protected abstract void Actualizar(Solution solucion);

    protected abstract void Incrementar();
}
