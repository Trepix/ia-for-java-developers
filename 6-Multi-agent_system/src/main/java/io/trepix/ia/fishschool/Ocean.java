package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;
import io.trepix.ia.MultiAgentSystem;
import io.trepix.ia.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ocean implements MultiAgentSystem {
    protected List<Fish> fishes;
    protected List<Obstacle> obstacles;
    protected Random generator;
    private final Size size;

    public Ocean(Size size, StartConfig startConfig) {
        this.generator = startConfig.generator();
        this.size = size;
        this.obstacles = new ArrayList<>();
        this.fishes = new ArrayList<>(startConfig.fishNumber());
        for (int i = 0; i < startConfig.fishNumber(); i++) {
            this.fishes.add(createFish());
        }
    }

    private Fish createFish() {
        double x = generator.nextDouble() * size.width();
        double y = generator.nextDouble() * size.height();
        double direction = generator.nextDouble() * 2 * Math.PI;
        return new Fish(new Position(x, y), Direction.fromRadians(direction));
    }

    public List<Fish> fishes() {
        return fishes;
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
    }
}
