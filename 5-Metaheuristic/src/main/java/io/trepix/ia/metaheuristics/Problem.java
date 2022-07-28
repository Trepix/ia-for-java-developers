package io.trepix.ia.metaheuristics;

import java.util.List;
import java.util.Optional;

public interface Problem {

    //Devuleve el vecindario de una solución
    List<Solution> neighbours(Solution solucionActual);

    Optional<Solution> bestNeighbour(Solution solucionActual);
    
    // Crea una solución aleatoria
    Solution randomSolution();
    
    // Devuelve la mejor solución de un conjunto
    Solution MejorSolucion(List<Solution> soluciones);
}
