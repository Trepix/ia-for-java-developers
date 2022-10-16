package io.trepix.ia.fishschool;

record Direction(double x, double y) {
    static Direction fromRadians(double radians) {
        var x = Math.cos(radians);
        var y = Math.sin(radians);
        return new Direction(x, y);
    }


    private Direction normalize() {
        double size = Math.sqrt(x * x + y * y);
        return new Direction(x/size, y/size);
    }

    public Direction rotateX(double radians) {
        return new Direction(x + radians, y).normalize();
    }

    public Direction rotateY(double radians) {
        return new Direction(x, y + radians).normalize();
    }
}
