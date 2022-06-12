package io.trepix.ia.fuzzylogic;

public class Line {
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

    Line(Point first, Point second) {
        this.first = first;
        this.second = second;
        this.slopeType = SlopeType.from(first.membershipDegree(), second.membershipDegree());
    }

    private double slope() {
        return (second.membershipDegree() - first.membershipDegree()) / (second.value() - first.value());
    }

    public double intersectionDistance(Line line) {
        return (line.first.membershipDegree() - this.first.membershipDegree()) / (this.slope() - line.slope());
    }
    boolean isIntersectingWith(Line line) {
        return !this.slopeType.equals(line.slopeType) && line.slopeType.isNotZero() && this.slopeType.isNotZero();
    }

    boolean isNotEqual(Line line) {
        return this.slope() != line.slope();
    }
}
