package io.trepix.ia.fishschool;

record Direction(double x, double y) {
    static Direction fromRadians(double radians) {
        var x = Math.cos(radians);
        var y = Math.sin(radians);
        return new Direction(x, y);
    }
}
