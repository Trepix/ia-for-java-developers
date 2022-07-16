package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.IndividualFactory;
import io.trepix.ia.geneticalgorithm.Configuration;
import io.trepix.ia.geneticalgorithm.Individual;

public class PathFactory extends IndividualFactory {
    private final Configuration configuration;
    private final Labyrinth labyrinth;

    public PathFactory(Configuration configuration) {
        this.configuration = configuration;
        this.labyrinth = new Labyrinth(Labyrinth.Map2);
    }

    @Override
    public Individual create() {
        return new Path(this.configuration, this.labyrinth);
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
