package io.trepix.ia.metaheuristics.algorithms;

import io.trepix.ia.metaheuristics.Algoritmo;
import io.trepix.ia.metaheuristics.IHM;
import io.trepix.ia.metaheuristics.IProblema;
import io.trepix.ia.metaheuristics.ISolucion;
import java.util.ArrayList;

// Descendiente de gradiente : se busca al mejor vecino hasta que no hay más mejorías
public abstract class DescensoGradiente extends Algoritmo {
    protected ISolucion solucionActual;
    
    @Override
    public final void Resolver(IProblema _pb, IHM ihm) {
        super.Resolver(_pb, ihm);
        
        solucionActual = problema.SolucionAleatoria();
        while(!CriterioParada()) {
            ArrayList<ISolucion> vecindario = problema.Vecindario(solucionActual);
            if (vecindario != null) {
                ISolucion mejorSolucion = problema.MejorSolucion(vecindario);
                Actualizar(mejorSolucion);
            }
            Incrementar();
        }
        EnviarResultado();
    }
    
    protected abstract boolean CriterioParada();
    protected abstract void Actualizar(ISolucion solucion);
    protected abstract void Incrementar();
}
