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
    private static final double MAX_RANGE_TO_BELONG_TO_FISH_SCHOOL = 40;

    private Position position;
    private Direction direction;

    public Fish(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Direction direction() {
        return direction;
    }

    public Position position() {
        return position;
    }

    public void evolve(Fish[] fishes, List<Obstacle> obstacles, Bounds bounds) {
        shiftInside(bounds);
        if (!(moveAwayFrom(bounds) || dodgeObstacles(obstacles) || dodgeFishes(asList(fishes)))) {
            followCurrentFishSchool(asList(fishes));
        }
        move();
    }

    private void shiftInside(Bounds bounds) {
        this.position = bounds.shiftToNearestBound(this.position);
    }

    private boolean moveAwayFrom(Bounds bounds) {
        Bound nearestBound = bounds.nearestTo(position);
        double distance = nearestBound.distanceTo(position);
        double ROTATE_DIRECTION = 0.3;
        if (distance < MIN_DISTANCE_TO_AVOID_COLLISION) {
            Direction rotation = switch (nearestBound.name()) {
                case LOWER_WIDTH -> new Direction(ROTATE_DIRECTION, 0);
                case UPPER_WIDTH -> new Direction(-ROTATE_DIRECTION, 0);
                case LOWER_HEIGHT -> new Direction(0, ROTATE_DIRECTION);
                case UPPER_HEIGHT -> new Direction(0, -ROTATE_DIRECTION);
            };
            this.direction = direction.sum(rotation).normalize();
            return true;
        }
        return false;
    }

    private boolean dodgeObstacles(List<Obstacle> obstacles) {
        if (obstacles.isEmpty()) {
            return false;
        }

        Obstacle nearestObstacle = obstacles.stream().min(comparing(this::distanceTo)).get();
        if (this.willCollideWith(nearestObstacle)) {
            Direction directionFromObstacle = nearestObstacle.directionTo(position);
            this.direction = direction.sum(directionFromObstacle.normalize().reduceBy(2)).normalize();
            return true;
        }
        return false;
    }

    private boolean willCollideWith(Obstacle nearestObstacle) {
        return this.distanceTo(nearestObstacle) < 2 * nearestObstacle.radius();
    }

    private double distanceTo(Obstacle obstacle) {
        return obstacle.distanceFrom(position);
    }

    private double distanceTo(Fish fish) {
        return fish.position.distanceTo(position);
    }

    private boolean dodgeFishes(List<Fish> fishes) {
        Fish nearestFish = fishes.stream().filter(fish -> !fish.equals(this)).min(comparing(this::distanceTo)).get();

        if (nearestFish.distanceTo(this) < MIN_DISTANCE_TO_AVOID_COLLISION) {
            Direction directionFromFish = nearestFish.directionTo(this);
            this.direction = direction.sum(directionFromFish.normalize().reduceBy(4)).normalize();
            return true;
        }
        return false;
    }

    private Direction directionTo(Fish fish) {
        return this.position.directionTo(fish.position);
    }

    private void followCurrentFishSchool(List<Fish> fishes) {
        var fishSchool = fishes.stream()
                .filter(this::belongsToMyFishSchool)
                .map(Fish::direction)
                .toList();

        var fishSchoolDirection = fishSchool.stream()
                .reduce(Direction::sum)
                .map(x -> x.reduceBy(fishSchool.size()));

        fishSchoolDirection.ifPresent(direction -> this.direction = this.direction.sum(direction).normalize());
    }

    private boolean belongsToMyFishSchool(Fish fish) {
        double distance = this.distanceTo(fish);
        return (distance < MAX_RANGE_TO_BELONG_TO_FISH_SCHOOL && distance > MIN_DISTANCE_TO_AVOID_COLLISION);
    }

    private void move() {
        this.position = position.move(direction, MOVING_DISTANCE);
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
