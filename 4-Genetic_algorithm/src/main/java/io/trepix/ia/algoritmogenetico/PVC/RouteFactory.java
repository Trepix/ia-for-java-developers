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
    public Individual reproduceFrom(Individual father) {
        return new Route((Route) father);
    }

    @Override
    public Individual reproduceFrom(Individual father1, Individual father2) {
        return new Route((Route) father1, (Route) father2);
    }
}
