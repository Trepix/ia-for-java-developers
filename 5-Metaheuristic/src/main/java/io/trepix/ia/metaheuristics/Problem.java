package io.trepix.ia.metaheuristics;

public interface Problem {

    Solutions neighbours(Solution solution);

    Solution randomSolution();

}
