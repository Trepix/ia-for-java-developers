package Metaheuristico.Algoritmos;

import Metaheuristico.Algoritmo;
import Metaheuristico.IHM;
import Metaheuristico.IProblema;
import Metaheuristico.ISolucion;
import java.util.ArrayList;

// Algoritmo del recorrido : se hacen aproximaciones cada vez más pequeñas con la temperatura que baja
public abstract class RecorridoSimulado extends Algoritmo {
    protected ISolucion solucionActual;
    protected ISolucion mejorSolucion;
    protected double temperatura;
    
    @Override
    public final void Resolver(IProblema pb, IHM ihm) {
        super.Resolver(pb, ihm);
        
        solucionActual = problema.SolucionAleatoria();
        mejorSolucion = solucionActual;
        InitializarTemperatura();
        
        while (!CriterioParada()) {
            ArrayList<ISolucion> vecindario = problema.Vecindario(solucionActual);
            if (vecindario != null) {
                ISolucion mejorVecino = problema.MejorSolucion(vecindario);
                Actualizar(mejorVecino);
            }
            Incrementar();
            ActualizarTemperatura();
        }
        EnviarResultado();
    }
    
    protected abstract void ActualizarTemperatura();
    protected abstract void InitializarTemperatura();
    protected abstract boolean CriterioParada();
    protected abstract void Actualizar(ISolucion solucion);
    protected abstract void Incrementar();
}
