package io.trepix.ia.fishschool;

public record Direction(double x, double y) {

    static Direction fromRadians(double radians) {
        var x = Math.cos(radians);
        var y = Math.sin(radians);
        return new Direction(x, y);
    }

    public Direction normalize() {
        return new Direction(x / hypotenuse(), y / hypotenuse());
    }

    private double hypotenuse() {
        return Math.sqrt(x * x + y * y);
    }

    public Direction sum(Direction rotation) {
        return new Direction(x + rotation.x(), y + rotation.y());
    }

    public Direction reduceBy(double ratio) {
        return new Direction(x / ratio, y / ratio);
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

}
