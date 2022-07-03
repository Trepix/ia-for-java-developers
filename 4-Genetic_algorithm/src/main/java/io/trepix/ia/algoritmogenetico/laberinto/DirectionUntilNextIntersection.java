package io.trepix.ia.algoritmogenetico.laberinto;

import io.trepix.ia.algoritmogenetico.Gene;
import io.trepix.ia.algoritmogenetico.Configuration;

// Los genes para el laberinto
public class DirectionUntilNextIntersection implements Gene {
    public Laberinto.Direccion dirección;
    private final Configuration configuration;
    
    public DirectionUntilNextIntersection(Configuration configuration) {
        this.configuration = configuration;
        dirección = Laberinto.Direccion.values()[configuration.random().nextInt(4)];
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
        dirección = Laberinto.Direccion.values()[configuration.random().nextInt(4)];
    }

}
