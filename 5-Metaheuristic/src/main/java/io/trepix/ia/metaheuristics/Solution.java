package io.trepix.ia.metaheuristics;
public interface Solution extends Comparable<Solution> {

    boolean isBetterThan(Solution solution);
    double value();
}
