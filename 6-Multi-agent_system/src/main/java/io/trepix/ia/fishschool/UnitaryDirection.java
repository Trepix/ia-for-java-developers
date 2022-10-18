package io.trepix.ia.fishschool;

public class UnitaryDirection extends Direction {

    public UnitaryDirection(double x, double y) {
        super(x / hypotenuse(x, y), y / hypotenuse(x, y));
    }

    private static double hypotenuse(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    static UnitaryDirection fromRadians(double radians) {
        var x = Math.cos(radians);
        var y = Math.sin(radians);
        return new UnitaryDirection(x, y);
    }

    public UnitaryDirection sum(Direction rotation) {
        return new UnitaryDirection(x + rotation.x(), y + rotation.y());
    }

    public Direction asDirection() {
        return new Direction(x, y);
    }
}
