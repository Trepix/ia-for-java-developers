package io.trepix.ia.fuzzylogic;

public class Slope {
    enum SlopeType {
        POSITIVE,
        ZERO,
        NEGATIVE;

        static SlopeType from(double first, double second) {
            int value = (int) Math.signum(first - second);
            if (value == 0) return SlopeType.ZERO;
            else if (value > 0) return SlopeType.NEGATIVE;
            else return SlopeType.POSITIVE;
        }

        private boolean isNotZero() {
            return !this.equals(SlopeType.ZERO);
        }
    }

    private final SlopeType slopeType;
    private final Point first;
    private final Point second;

    Slope(Point first, Point second) {
        this.first = first;
        this.second = second;
        this.slopeType = SlopeType.from(first.membershipDegree(), second.membershipDegree());
    }

    private double value() {
        return (second.membershipDegree() - first.membershipDegree()) / (second.value() - first.value());
    }

    public double intersectionDistance(Slope slope) {
        return (slope.first.membershipDegree() - this.first.membershipDegree()) / (this.value() - slope.value());
    }
    boolean isIntersectingWith(Slope slope) {
        return !this.slopeType.equals(slope.slopeType) && slope.slopeType.isNotZero() && this.slopeType.isNotZero();
    }

    boolean isNotEqual(Slope slope) {
        return this.value() != slope.value();
    }
}
