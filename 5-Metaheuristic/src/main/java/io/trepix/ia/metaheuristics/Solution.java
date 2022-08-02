package io.trepix.ia.metaheuristics;

import java.util.Random;

public interface Solution<S> extends Comparable<Solution<S>> {

    boolean isBetterThan(S solution);
    double value();

    void improveWith(S betterSolution, Random random);
}
