package io.trepix.ia.metaheuristics;

public abstract class Algorithm<P extends Problem<S>, S extends Solution<S>> {
    private final String name;

    protected Algorithm(String name) {
        this.name = name;
    }

    public abstract S solve(P problem);

    public String name() {
        return this.name;
    }
}
