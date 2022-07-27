package io.trepix.ia.metaheuristics;
public abstract class Algorithm<T extends Problem> {
    private final String name;

    protected Algorithm(String name) {
        this.name = name;
    }
    public abstract Solution solve(T problem);

    public String name() {
        return this.name;
    }
}
