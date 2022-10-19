package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;

import java.util.Objects;

public record Position(double x, double y) {

    public Position shiftInside(Bounds bounds) {
        if (this.isInside(bounds)) return this;

        var x = this.x;
        var y = this.y;
        if (x < bounds.lowerWidth()) {
            x = bounds.lowerWidth();
        }
        else if (y < bounds.lowerHeight()) {
            y = bounds.lowerHeight();
        }
        else if (x > bounds.upperWidth()) {
            x = bounds.upperWidth();
        }
        else if (y > bounds.upperHeight()) {
            y = bounds.upperHeight();
        }
        return new Position(x, y);
    }

    public double distanceTo(Position position) {
        return Math.sqrt((x- position.x)*(x- position.x)+(y-position.y)*(y-position.y));
    }

    private boolean isInside(Bounds bounds) {
        if (x < bounds.lowerWidth() || x > bounds.upperWidth()) return false;
        if (y < bounds.lowerHeight() || y > bounds.upperHeight()) return false;
        return true;
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
