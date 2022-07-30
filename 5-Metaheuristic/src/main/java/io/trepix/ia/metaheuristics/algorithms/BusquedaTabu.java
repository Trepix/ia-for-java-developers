package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.List;

// Búsqueda tabu : nos movemos al mejor vocino no presente en la lista tabu
public abstract class BusquedaTabu<T extends Problem> extends Algorithm<T> {

    protected Problem problem;
    protected Solution solucionActual;
    protected Solution mejorSolucion;

    public BusquedaTabu() {
        super("Tabu Search");
    }

    @Override
    public final Solution solve(Problem problem) {
        this.problem = problem;

        solucionActual = this.problem.SolucionAleatoria();
        mejorSolucion = solucionActual;
        AgregarListaTabu(solucionActual);

        while (!CriterioParada()) {
            List<Solution> vecindario = this.problem.Vecindario(solucionActual);
            if (vecindario != null) {
                vecindario = EliminarSolucionesTabues(vecindario);
                Solution mejorVecino = this.problem.MejorSolucion(vecindario);
                if (mejorVecino != null) {
                    Actualizar(mejorVecino);
                }
            }
            Incrementar();
        }
        return mejorSolucion;
    }

    @Override
    public final Solution _solve(T problem) {
        return this.solve(problem);
    }

    protected abstract void AgregarListaTabu(Solution solucion);

    protected abstract List<Solution> EliminarSolucionesTabues(List<Solution> vecindario);

    protected abstract boolean CriterioParada();

    protected abstract void Actualizar(Solution solucion);

    protected abstract void Incrementar();
}
