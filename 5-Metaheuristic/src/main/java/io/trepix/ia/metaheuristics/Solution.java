package io.trepix.ia.metaheuristics;
public interface Solution extends Comparable<Solution> {

    default boolean isBetterThan(Solution solution) {
        return this.compareTo(solution) > 0;
    }
    double value();
}
