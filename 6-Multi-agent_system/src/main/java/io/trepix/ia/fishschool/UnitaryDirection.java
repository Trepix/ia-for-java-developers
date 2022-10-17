package io.trepix.ia.fishschool;

public class UnitaryDirection {

    private final double x;
    private final double y;

    private static final double ROTATION = 0.3;

    public UnitaryDirection(double x, double y) {
        double size = Math.sqrt(x * x + y * y);
        this.x = x / size;
        this.y = y / size;
    }

    static UnitaryDirection fromRadians(double radians) {
        var x = Math.cos(radians);
        var y = Math.sin(radians);
        return new UnitaryDirection(x, y);
    }

    public UnitaryDirection turnToRight() {
        return new UnitaryDirection(x + ROTATION, y);
    }

    public UnitaryDirection turnToLeft() {
        return new UnitaryDirection(x - ROTATION, y);
    }

    public UnitaryDirection turnUpwards() {
        return new UnitaryDirection(x, y + ROTATION);
    }

    public UnitaryDirection turnDownwards() {
        return new UnitaryDirection(x, y - ROTATION);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public UnitaryDirection turnAwayFrom(UnitaryDirection direction) {
        return new UnitaryDirection(x - direction.x / 2, y - direction.y / 2);
    }
}
