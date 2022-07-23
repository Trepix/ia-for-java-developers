package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algorithm;
import io.trepix.ia.metaheuristics.IHM;
import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
import java.util.ArrayList;
import java.util.List;

// Descendiente de gradiente : se busca al mejor vecino hasta que no hay más mejorías
public abstract class DescensoGradiente extends Algorithm {
    protected Solution solucionActual;
    
    @Override
    public final void Resolver(Problem problem, IHM ihm) {
        super.Resolver(problem, ihm);
        
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
