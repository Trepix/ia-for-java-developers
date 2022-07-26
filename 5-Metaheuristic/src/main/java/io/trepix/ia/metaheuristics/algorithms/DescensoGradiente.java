package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.List;

// Descendiente de gradiente : se busca al mejor vecino hasta que no hay más mejorías
public abstract class DescensoGradiente extends Algorithm {

    protected Problem problem;
    protected Solution solucionActual;

    public DescensoGradiente() {
        super("Gradient Descent");
    }

    @Override
    public final Solution solve(Problem problem) {
        this.problem = problem;

        solucionActual = this.problem.SolucionAleatoria();
        while (!CriterioParada()) {
            List<Solution> vecindario = this.problem.Vecindario(solucionActual);
            if (vecindario != null) {
                Solution mejorSolucion = this.problem.MejorSolucion(vecindario);
                Actualizar(mejorSolucion);
            }
            Incrementar();
        }
        return solucionActual;
    }

    protected abstract boolean CriterioParada();

    protected abstract void Actualizar(Solution solucion);

    protected abstract void Incrementar();
}
