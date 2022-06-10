package io.trepix.ia.fuzzylogic;

public record Point(double value, double membershipDegree) implements Comparable<Point> {

    public Point applyDegree(double degree) {
        return new Point(value, membershipDegree * degree);
    }
    public boolean isValue(double value) {
        return value() == value;
    }

    public boolean isSameValue(Point point) {
        return isValue(point.value());
    }

    public boolean isPreviousTo(Point secondPoint) {
        return this.value < secondPoint.value();
    }

    public Point max(Point point) {
        if (point == null) return this;
        return new Point(value, Math.max(membershipDegree, point.membershipDegree));
    }

    public Slope slope(Point point) {
        return new Slope(this, point);
    }

    public Point interpolateAt(Point second, double value) {
        /* Thales theorem
                       ⎛y  - y ⎞ . ⎛x  - x⎞
        L1   H1        ⎝ 1    2⎠   ⎝ 2    ⎠
        ── = ── ; y = ──────────────────── + y
        L2   H2               x  - x          2
                               2    1
        */
        double memberShipDegree = (membershipDegree() - second.membershipDegree()) * (second.value() - value) /
                (second.value() - value())
                + second.membershipDegree();
        return new Point(value, memberShipDegree);
    }

    public static Point leftmostPoint(Point p1, Point p2) {
        if (p1.isPreviousTo(p2)) return p1;
        else return p2;
    }

    public static Point rightestPoint(Point p1, Point p2) {
        if (p1.isPreviousTo(p2)) return p2;
        else return p1;
    }
    @Override
    public int compareTo(Point t) {
        return (int) (value - t.value);
    }

    @Override
    public String toString() {
        return "(" + value + ";" + membershipDegree + ")";
    }

}
