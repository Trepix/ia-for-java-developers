package io.trepix.ia.fuzzylogic.geometry;

import static java.lang.Math.signum;

public enum Trend {
    POSITIVE,
    ZERO,
    NEGATIVE;

    public static Trend given(Point first, Point second) {
        int value = (int) signum(first.membershipDegree() - second.membershipDegree());
        if (value == 0) return ZERO;
        else if (value > 0) return NEGATIVE;
        else return POSITIVE;
    }

    public boolean isInvertedRespectTo(Trend trend) {
        return this.equals(POSITIVE) && trend.equals(NEGATIVE) ||
                this.equals(NEGATIVE) && trend.equals(POSITIVE);
    }
}
