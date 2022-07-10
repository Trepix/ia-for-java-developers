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
    public Individual reproduceFrom(Individual father) {
        return new Path((Path) father);
    }

    @Override
    public Individual reproduceFrom(Individual father1, Individual father2) {
        return new Path((Path) father1, (Path) father2);
    }
}
