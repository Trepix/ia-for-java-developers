package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.Gene;
import io.trepix.ia.geneticalgorithm.Configuration;

public class DirectionUntilNextIntersection implements Gene {
    public Direction direction;
    private final Configuration configuration;
    
    public DirectionUntilNextIntersection(Configuration configuration) {
        this.configuration = configuration;
        this.direction = Direction.pickOne(configuration.random());
    }
    
    public DirectionUntilNextIntersection(DirectionUntilNextIntersection directionUntilNextIntersection) {
        this.configuration = directionUntilNextIntersection.configuration;
        this.direction = directionUntilNextIntersection.direction;
    }
    
    @Override
    public String toString() {
        return direction.toString();
    }
    
    @Override
    public void mutate() {
        direction = Direction.pickOne(configuration.random());
    }

}
