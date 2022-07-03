package io.trepix.ia.algoritmogenetico.PVC;

import io.trepix.ia.algoritmogenetico.IndividualFactory;
import io.trepix.ia.algoritmogenetico.Configuration;
import io.trepix.ia.algoritmogenetico.Individual;

public class RouteFactory extends IndividualFactory {
    private final Configuration configuration;

    public RouteFactory(Configuration configuration) {
        this.configuration = configuration;
        TravellingSalesmanProblem.Init();
    }

    @Override
    public Individual create() {
        return new Route(this.configuration);
    }

    @Override
    public Individual createFrom(Individual padre) {
        return new Route((Route) padre);
    }

    @Override
    public Individual createFrom(Individual padre1, Individual padre2) {
        return new Route((Route) padre1, (Route) padre2);
    }
}
