package io.trepix.ia.fuzzylogic.geometry;

import static io.trepix.ia.fuzzylogic.geometry.Trend.ZERO;
import static io.trepix.ia.fuzzylogic.geometry.Trend.given;

public class Line {

    final Trend trend;
    private final Point first;
    private final Point second;

    public Line(Point first, Point second) {
        this.first = first;
        this.second = second;
        this.trend = given(first, second);
    }

    private double slope() {
        return (second.distanceBetweenMembershipDegree(first)) / (second.distanceBetweenValues(first));
    }

    public double intersectionDistance(Line line) {
        return (line.first.distanceBetweenMembershipDegree(first)) / (this.slope() - line.slope());
    }

    public boolean hasZeroSlope() {
        return trend.equals(ZERO);
    }

    public boolean isNotEqual(Line line) {
        return this.slope() != line.slope();
    }
}
