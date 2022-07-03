package io.trepix.ia.algoritmogenetico.laberinto;

import io.trepix.ia.algoritmogenetico.IndividualFactory;
import io.trepix.ia.algoritmogenetico.Configuration;
import io.trepix.ia.algoritmogenetico.Individual;

public class PathFactory extends IndividualFactory {
    private final Configuration configuration;

    public PathFactory(Configuration configuration) {
        this.configuration = configuration;
        Laberinto.Init(Laberinto.Lab2);
    }

    @Override
    public Individual create() {
        return new Path(this.configuration);
    }

    @Override
    public Individual createFrom(Individual padre) {
        return new Path((Path) padre);
    }

    @Override
    public Individual createFrom(Individual padre1, Individual padre2) {
        return new Path((Path) padre1, (Path) padre2);
    }
}
