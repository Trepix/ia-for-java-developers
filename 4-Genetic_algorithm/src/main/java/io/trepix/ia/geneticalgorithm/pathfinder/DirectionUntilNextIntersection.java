package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.Gene;
import io.trepix.ia.geneticalgorithm.Configuration;

public class DirectionUntilNextIntersection implements Gene {
    private Direction direction;
    private final Configuration configuration;
    
    public DirectionUntilNextIntersection(Configuration configuration) {
        this.configuration = configuration;
        this.direction = Direction.pickOne(configuration.random());
    }

    @Override
    public String toString() {
        return direction.toString();
    }
    
    @Override
    public void mutate() {
        direction = Direction.pickOne(configuration.random());
    }

    public Direction direction() {
        return direction;
    }

    public DirectionUntilNextIntersection copy() {
        var clone = new DirectionUntilNextIntersection(this.configuration);
        clone.direction = this.direction;
        return clone;
    }

}
