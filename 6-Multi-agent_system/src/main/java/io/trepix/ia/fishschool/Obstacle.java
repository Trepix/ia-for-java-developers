package io.trepix.ia.fishschool;

import java.util.Objects;

public class Obstacle {
    public static final int RADIUS = 10;
    private final Position position;
    private int aliveCycles = 500;

    public Obstacle(Position position) {
        this.position = position;
    }

    public Position position() {
        return position;
    }

    public void evolve() {
        aliveCycles--;
    }

    public boolean isDead() {
        return aliveCycles <= 0;
    }

    public double radius() {
        return RADIUS;
    }

    public double distanceFrom(Position position) {
        return position.distanceTo(this.position);
    }

    public Direction directionTo(Position position) {
        return this.position.directionTo(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Obstacle obstacle)) return false;
        return aliveCycles == obstacle.aliveCycles && position.equals(obstacle.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, aliveCycles);
    }
}
