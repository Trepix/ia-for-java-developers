package io.trepix.ia.metaheuristics;

import java.util.List;

public interface Problem {

    //Devuleve el vecindario de una solución
    List<Solution> Vecindario(Solution solucionActual);
    
    // Crea una solución aleatoria
    Solution SolucionAleatoria();
    
    // Devuelve la mejor solución de un conjunto
    Solution MejorSolucion(List<Solution> soluciones);
}
