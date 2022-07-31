package io.trepix.ia.metaheuristics;

import java.util.List;

public interface Problem {

    Solutions neighbours(Solution solution);

    Solution randomSolution();
    
    // Devuelve la mejor solución de un conjunto
    Solution MejorSolucion(List<Solution> soluciones);
}
