package io.trepix.ia.fishschool;

import java.util.Objects;

public record Position(double x, double y) {

    public double distanceTo(Position position) {
        return Math.sqrt((x- position.x)*(x- position.x)+(y-position.y)*(y-position.y));
    }

    public Direction directionTo(Position position) {
        double xDirection = (position.x() - this.x());
        double yDirection = (position.y() - this.y());
        return new Direction(xDirection, yDirection);
    }

    public Position move(Direction direction, double distance) {
        return new Position(x + direction.x() * distance, y + direction.y() * distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        double epsilon = 1E-10;
        return equals(position.x, x, epsilon) && equals(position.y, y, epsilon);
    }

    private boolean equals(double d1, double d2, double epsilon) {
        return Math.abs(d1 - d2) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
