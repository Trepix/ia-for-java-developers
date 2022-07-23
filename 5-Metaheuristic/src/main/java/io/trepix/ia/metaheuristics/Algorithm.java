package io.trepix.ia.metaheuristics;
public abstract class Algorithm {
    protected Problem problem;
    protected IHM ihm;
    
    public void Resolver(Problem problem, IHM _ihm) {
        this.problem = problem;
        ihm = _ihm;
    }
    
    protected abstract void sendResult();
}
