package io.trepix.ia.Metaheuristico;

import java.util.ArrayList;

// Un problema genérico
public interface IProblema {
    // Devuelve el vecindario de una solución
    ArrayList<ISolucion> Vecindario(ISolucion solucionActual);
    
    // Crea una solución aleatoria
    ISolucion SolucionAleatoria();
    
    // Devuelve la mejor solución de un conjunto
    ISolucion MejorSolucion(ArrayList<ISolucion> soluciones);
}
