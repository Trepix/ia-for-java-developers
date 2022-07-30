package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.List;

// Algoritmo del recorrido : se hacen aproximaciones cada vez más pequeñas con la temperatura que baja
public abstract class RecocidoSimulado<T extends Problem> extends Algorithm<T> {

    protected Problem problem;
    protected Solution solucionActual;
    protected Solution mejorSolucion;
    protected double temperatura;

    public RecocidoSimulado() {
        super("Simulated Annealing");
    }

    @Override
    public final Solution solve(Problem problem) {
        this.problem = problem;

        solucionActual = this.problem.SolucionAleatoria();
        mejorSolucion = solucionActual;
        InitializarTemperatura();

        while (!CriterioParada()) {
            List<Solution> vecindario = this.problem.Vecindario(solucionActual);
            if (vecindario != null) {
                Solution mejorVecino = this.problem.MejorSolucion(vecindario);
                Actualizar(mejorVecino);
            }
            Incrementar();
            ActualizarTemperatura();
        }
        return mejorSolucion;
    }

    @Override
    public final Solution _solve(T problem) {
        return this.solve(problem);
    }

    protected abstract void ActualizarTemperatura();

    protected abstract void InitializarTemperatura();

    protected abstract boolean CriterioParada();

    protected abstract void Actualizar(Solution solucion);

    protected abstract void Incrementar();
}
