package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;
import io.trepix.ia.Size;
import io.trepix.ia.gameoflife.MultiAgentSystem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static java.util.Arrays.asList;

public class Ocean implements MultiAgentSystem {
    protected Fish[] fishes;
    protected List<Obstacle> obstacles;
    protected Random generator;
    private final PropertyChangeSupport support;

    private final Size size;

    public void AgregarChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public Ocean(Size size, StartConfig startConfig) {
        this.support = new PropertyChangeSupport(this);
        this.generator = startConfig.generator();
        this.size = size;
        this.obstacles = new ArrayList<>();
        this.fishes = new Fish[startConfig.fishNumber()];
        for (int i = 0; i < startConfig.fishNumber(); i++) {
            fishes[i] = createFish();
        }
    }

    private Fish createFish() {
        double x = generator.nextDouble() * size.width();
        double y = generator.nextDouble() * size.height();
        double direction = generator.nextDouble() * 2 * Math.PI;
        return new Fish(new Position(x, y), UnitaryDirection.fromRadians(direction));
    }

    public List<Fish> fishes() {
        return asList(fishes);
    }

    public List<Obstacle> obstacles() {
        return obstacles;
    }

    public void addObstacleAt(Position position) {
        obstacles.add(new Obstacle(position));
    }
    
    protected void evolveObstacles() {
        obstacles.forEach(Obstacle::evolve);
        obstacles.removeIf(Obstacle::isDead);
    }
    
    protected void evolveFishes() {
        var bounds = new Bounds(0, size.width(), 0, size.height());
        for (Fish fish : fishes) {
            fish.evolve(fishes, obstacles, bounds);
        }
    }

    @Override
    public void evolve() {
        evolveObstacles();
        evolveFishes();
        support.firePropertyChange("changed", 0, 1);
    }
}
