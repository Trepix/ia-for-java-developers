package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;
import io.trepix.ia.Bounds.Bound;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

public class Fish extends Objeto {
    private static final double MOVING_DISTANCE = 3;
    private static final double MIN_DISTANCE_TO_AVOID_COLLISION = 5;
    private static final double DISTANCIA_MIN_CUADRADO = 25;
    private static final double DISTANCIA_MAX_CUADRADO = 1600;

    protected double velocidadX;
    protected double velocidadY;

    private Position position;

    private UnitaryDirection direction;

    public Fish(Position position, UnitaryDirection direction) {
        updatePosition(position);
        updateDirection(direction);
    }

    protected void move() {
        updatePosition(getPosition().move(getDirection(), MOVING_DISTANCE));
    }

    protected boolean Alineado(Fish p) {
        double distanciaCuadrado = DistanciaCuadrado(p);
        return (distanciaCuadrado < DISTANCIA_MAX_CUADRADO && distanciaCuadrado > DISTANCIA_MIN_CUADRADO);
    }

    protected boolean dodgeObstacles(List<Obstacle> obstacles) {
        if (obstacles.isEmpty()) {
            return false;
        }

        Obstacle nearestObstacle = obstacles.stream().min(comparing(this::distanceTo)).get();
        if (this.willCollideWith(nearestObstacle)) {
            UnitaryDirection directionFromObstacle = nearestObstacle.directionTo(this.getPosition());
            updateDirection(
                    getDirection().sum(
                            directionFromObstacle.reduceBy(2)));
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
            UnitaryDirection directionFromFish = nearestFish.getPosition().directionTo(this.getPosition());
            updateDirection(
                    getDirection().sum(
                            directionFromFish.reduceBy(4)));
            return true;
        }
        return false;
    }

    protected void CalcularDireccionMedia(List<Fish> fishes) {
        var fishSchool = fishes.stream()
                .filter(this::Alineado)
                .map(Fish::getDirection)
                .map(UnitaryDirection::asDirection)
                .toList();

        var fishSchoolDirection = fishSchool.stream()
                .reduce(Direction::sum)
                .map(x -> x.reduceBy(fishSchool.size()));

        fishSchoolDirection.ifPresent(x -> updateDirection(getDirection().sum(x)));
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
            updateDirection(getDirection().sum(rotation));
            return true;
        }
        return false;
    }

    //Narrowed change methods

    private Position getPosition() {
        return this.position;
    }

    private void updatePosition(Position position) {
        posX = position.x();
        posY = position.y();
        this.position = position;
    }

    private UnitaryDirection getDirection() {
        return this.direction;
    }

    private void updateDirection(UnitaryDirection direction) {
        velocidadX = direction.x();
        velocidadY = direction.y();
        this.direction = direction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fish fish)) return false;
        double epsilon = 1E-10;
        return equals(fish.velocidadX, velocidadX, epsilon) && equals(fish.velocidadY, velocidadY, epsilon) &&
                super.equals(o);
    }

    private boolean equals(double d1, double d2, double epsilon) {
        return Math.abs(d1 - d2) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocidadX, velocidadY);
    }
}
