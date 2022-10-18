package io.trepix.ia.fishschool;

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
}
