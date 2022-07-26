package io.trepix.ia.metaheuristics;
public abstract class Algorithm {
    private final String name;
    protected Problem problem;

    protected Algorithm(String name) {
        this.name = name;
    }

    public Solution solve(Problem problem) {
        this.problem = problem;
        return null;
    }

    public String name() {
        return this.name;
    }
}
