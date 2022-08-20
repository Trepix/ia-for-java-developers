package io.trepix.ia.fishschool;

public class Obstacle extends Objeto {
    public static final int RADIUS = 10;
    private final Position position;
    private int aliveCycles = 500;

    public Obstacle(Position position) {
        this.position = position;
        posX = position.x();
        posY = position.y();
    }

    public double x() {
        return position.x();
    }

    public double y() {
        return position.y();
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
}
