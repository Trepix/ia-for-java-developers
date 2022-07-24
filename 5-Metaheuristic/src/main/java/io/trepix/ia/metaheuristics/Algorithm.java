package io.trepix.ia.metaheuristics;
public abstract class Algorithm {
    private final String name;
    protected Problem problem;
    protected HumanMachineInterface ihm;

    protected Algorithm(String name) {
        this.name = name;
    }
    
    public void Resolver(Problem problem, HumanMachineInterface _ihm) {
        this.problem = problem;
        ihm = _ihm;
    }
    
    protected abstract void sendResult();
    public String name() {
        return this.name;
    }
}
