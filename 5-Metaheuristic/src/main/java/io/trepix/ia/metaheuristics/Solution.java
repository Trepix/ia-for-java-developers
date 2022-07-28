package io.trepix.ia.metaheuristics;
public interface Solution {

    boolean isBetterThan(Solution solution);
    double value();
}
