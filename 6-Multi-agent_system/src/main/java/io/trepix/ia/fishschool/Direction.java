package io.trepix.ia.fishschool;

import java.util.Objects;

public class Direction {

    final double x;
    final double y;

    public Direction(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public Direction reduceBy(double ratio) {
        return new Direction(x / ratio, y / ratio);
    }

    public Direction sum(Direction direction) {
        return new Direction(x + direction.x, y + direction.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Direction direction)) return false;
        double epsilon = 1E-10;
        return equals(direction.x, x, epsilon) && equals(direction.y, y, epsilon);
    }

    private boolean equals(double d1, double d2, double epsilon) {
        return Math.abs(d1 - d2) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
