package io.trepix.ia.fishschool;

record Direction(double x, double y) {

    private static final double ROTATION = 0.3;
    static Direction fromRadians(double radians) {
        var x = Math.cos(radians);
        var y = Math.sin(radians);
        return new Direction(x, y);
    }


    private Direction normalize() {
        double size = Math.sqrt(x * x + y * y);
        return new Direction(x/size, y/size);
    }

    public Direction turnToRight() {
        return new Direction(x + ROTATION, y).normalize();
    }

    public Direction turnToLeft() {
        return new Direction(x - ROTATION, y).normalize();
    }

    public Direction turnUpwards() {
        return new Direction(x, y + ROTATION).normalize();
    }

    public Direction turnDownwards() {
        return new Direction(x, y - ROTATION).normalize();
    }
}
