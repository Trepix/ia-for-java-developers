package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.Gene;
import io.trepix.ia.geneticalgorithm.Configuration;

// Los genes para el laberinto
public class DirectionUntilNextIntersection implements Gene {
    public Direction dirección;
    private final Configuration configuration;
    
    public DirectionUntilNextIntersection(Configuration configuration) {
        this.configuration = configuration;
        dirección = Direction.values()[configuration.random().nextInt(4)];
    }
    
    public DirectionUntilNextIntersection(DirectionUntilNextIntersection g) {
        this.configuration = g.configuration;
        dirección = g.dirección;
    }
    
    @Override
    public String toString() {
        return dirección.name().substring(0, 1);
    }
    
    @Override
    public void mutate() {
        dirección = Direction.values()[configuration.random().nextInt(4)];
    }

}
