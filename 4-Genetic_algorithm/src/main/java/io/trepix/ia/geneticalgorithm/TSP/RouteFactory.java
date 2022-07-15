package io.trepix.ia.geneticalgorithm.TSP;

import io.trepix.ia.geneticalgorithm.IndividualFactory;
import io.trepix.ia.geneticalgorithm.Configuration;
import io.trepix.ia.geneticalgorithm.Individual;

import java.util.List;

public class RouteFactory extends IndividualFactory {
    private final Configuration configuration;
    private final List<City> cities;

    public RouteFactory(Configuration configuration, List<City> cities) {
        this.configuration = configuration;
        this.cities = cities;
    }

    @Override
    public Individual create() {
        return new Route(this.configuration, this.cities);
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
