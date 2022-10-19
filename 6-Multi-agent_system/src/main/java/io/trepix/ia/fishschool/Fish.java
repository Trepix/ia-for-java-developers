package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;
import io.trepix.ia.Bounds.Bound;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

public class Fish {
    private static final double MOVING_DISTANCE = 3;
    private static final double MIN_DISTANCE_TO_AVOID_COLLISION = 5;
    private static final double DISTANCIA_MIN_CUADRADO = 25;
    private static final double DISTANCIA_MAX_CUADRADO = 1600;

    private Position position;
    private Direction direction;

    public Fish(Position position, Direction direction) {
        updatePosition(position);
        updateDirection(direction);
    }

    public Direction direction() {
        return direction;
    }

    public Position position() {
        return position;
    }

    protected void move() {
        updatePosition(getPosition().move(getDirection(), MOVING_DISTANCE));
    }

    protected boolean Alineado(Fish p) {
        double distanciaCuadrado = this.distanceTo(p) * this.distanceTo(p);
        return (distanciaCuadrado < DISTANCIA_MAX_CUADRADO && distanciaCuadrado > DISTANCIA_MIN_CUADRADO);
    }

    protected boolean dodgeObstacles(List<Obstacle> obstacles) {
        if (obstacles.isEmpty()) {
            return false;
        }

        Obstacle nearestObstacle = obstacles.stream().min(comparing(this::distanceTo)).get();
        if (this.willCollideWith(nearestObstacle)) {
            Direction directionFromObstacle = nearestObstacle.directionTo(this.getPosition());
            updateDirection(
                    getDirection().sum(
                            directionFromObstacle.normalize().reduceBy(2)).normalize());
            return true;
        }
        return false;
    }

    private boolean willCollideWith(Obstacle nearestObstacle) {
        return this.distanceTo(nearestObstacle) < 2 * nearestObstacle.radius();
    }

    private double distanceTo(Obstacle obstacle) {
        return obstacle.distanceFrom(getPosition());
    }

    private double distanceTo(Fish fish) {
        return fish.getPosition().distanceTo(getPosition());
    }

    protected boolean dodgeFishes(List<Fish> fishes) {
        Fish nearestFish = fishes.stream().filter(fish -> !fish.equals(this)).min(comparing(this::distanceTo)).get();

        if (nearestFish.distanceTo(this) < MIN_DISTANCE_TO_AVOID_COLLISION) {
            Direction directionFromFish = nearestFish.getPosition().directionTo(this.getPosition());
            updateDirection(
                    getDirection().sum(
                            directionFromFish.normalize().reduceBy(4)).normalize());
            return true;
        }
        return false;
    }

    protected void CalcularDireccionMedia(List<Fish> fishes) {
        var fishSchool = fishes.stream()
                .filter(this::Alineado)
                .map(Fish::getDirection)
                .toList();

        var fishSchoolDirection = fishSchool.stream()
                .reduce(Direction::sum)
                .map(x -> x.reduceBy(fishSchool.size()));

        fishSchoolDirection.ifPresent(x -> updateDirection(getDirection().sum(x).normalize()));
    }

    protected void evolve(Fish[] fishes, List<Obstacle> obstacles, Bounds bounds) {
        shiftInside(bounds);
        if (!(moveAwayFrom(bounds) || dodgeObstacles(obstacles) || dodgeFishes(asList(fishes)))) {
            CalcularDireccionMedia(asList(fishes));
        }
        move();
    }

    private void shiftInside(Bounds bounds) {
        var position = getPosition().shiftInside(bounds);
        updatePosition(position);
    }

    private boolean moveAwayFrom(Bounds bounds) {
        Bound nearestBound = bounds.nearestTo(getPosition());
        double distance = nearestBound.distanceTo(getPosition());
        double ROTATE_DIRECTION = 0.3;
        if (distance < MIN_DISTANCE_TO_AVOID_COLLISION) {
            Direction rotation = switch (nearestBound.name()) {
                case LOWER_WIDTH -> new Direction(ROTATE_DIRECTION, 0);
                case UPPER_WIDTH -> new Direction(-ROTATE_DIRECTION, 0);
                case LOWER_HEIGHT -> new Direction(0, ROTATE_DIRECTION);
                case UPPER_HEIGHT -> new Direction(0, -ROTATE_DIRECTION);
            };
            updateDirection(getDirection().sum(rotation).normalize());
            return true;
        }
        return false;
    }

    //Narrowed change methods

    private Position getPosition() {
        return this.position;
    }

    private void updatePosition(Position position) {
        this.position = position;
    }

    private Direction getDirection() {
        return this.direction;
    }

    private void updateDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fish fish)) return false;
        return direction.equals(fish.direction) && position.equals(fish.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, position);
    }
}
