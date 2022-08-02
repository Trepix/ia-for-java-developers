package io.trepix.ia.metaheuristics;
public interface Solution<S> extends Comparable<Solution<S>> {

    boolean isBetterThan(S solution);
    double value();
}
