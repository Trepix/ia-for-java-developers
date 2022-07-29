package io.trepix.ia.metaheuristics;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Solutions {

    private final List<Solution> solutions;

    public Solutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public Optional<Solution> best() {
        return solutions.stream().max(Solution::compareTo);
    }

    public void remove(Collection<Solution> solutions) {
        this.solutions.removeAll(solutions);
    }
}
