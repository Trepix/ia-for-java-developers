package io.trepix.ia.metaheuristics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Solutions<S extends Solution<S>> implements Iterable<S> {

    private final List<S> solutions;

    public Solutions(List<S> solutions) {
        this.solutions = solutions;
    }

    public Optional<S> best() {
        return solutions.stream().max(Solution::compareTo);
    }

    public void remove(Collection<S> solutions) {
        this.solutions.removeAll(solutions);
    }

    @Override
    public Iterator<S> iterator() {
        return solutions.iterator();
    }

}
