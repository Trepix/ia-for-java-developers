package io.trepix.ia.metaheuristics;
public abstract class Algorithm {
    private final String name;
    protected Problem problem;

    protected Algorithm(String name) {
        this.name = name;
    }
    
    public void solve(Problem problem) {
        this.problem = problem;
    }

    public Solution _solve(Problem problem) {
        this.problem = problem;
        return null;
    }

    public String name() {
        return this.name;
    }
}
