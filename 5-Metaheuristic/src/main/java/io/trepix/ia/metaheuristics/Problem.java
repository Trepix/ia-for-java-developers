package io.trepix.ia.metaheuristics;

import java.util.List;
import java.util.Optional;

public interface Problem {

    //Devuleve el vecindario de una solución
    List<Solution> neighbours(Solution solucionActual);

    Solutions _neighbours(Solution solution);

    Solution randomSolution();
    
    // Devuelve la mejor solución de un conjunto
    Solution MejorSolucion(List<Solution> soluciones);
}
