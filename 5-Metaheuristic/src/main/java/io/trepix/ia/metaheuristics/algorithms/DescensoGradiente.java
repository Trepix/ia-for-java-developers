package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;

import java.util.List;

// Descendiente de gradiente : se busca al mejor vecino hasta que no hay más mejorías
public abstract class DescensoGradiente extends Algorithm {
    protected Solution solucionActual;

    public DescensoGradiente() {
        super("Descenso de gradiente");
    }
    
    @Override
    public final void solve(Problem problem) {
        super.solve(problem);
        
        solucionActual = this.problem.SolucionAleatoria();
        while(!CriterioParada()) {
            List<Solution> vecindario = this.problem.Vecindario(solucionActual);
            if (vecindario != null) {
                Solution mejorSolucion = this.problem.MejorSolucion(vecindario);
                Actualizar(mejorSolucion);
            }
            Incrementar();
        }
        sendResult();
    }
    
    protected abstract boolean CriterioParada();
    protected abstract void Actualizar(Solution solucion);
    protected abstract void Incrementar();
}
