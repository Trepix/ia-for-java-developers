package io.trepix.ia.fuzzylogic;

import java.util.LinkedList;
import java.util.List;

public abstract class FuzzySetBuilder {

    protected double minimum;
    protected double maximum;

    public FuzzySetBuilder changeMinimum(double minimum) {
        this.minimum = minimum;
        return this;
    }

    public FuzzySetBuilder changeMaximum(double maximum) {
        this.maximum = maximum;
        return this;
    }

    public static OpenLeftTrapezoidalFuzzySetBuilder OpenLeftTrapezoidal() {
        return new OpenLeftTrapezoidalFuzzySetBuilder();
    }

    public static OpenRightTrapezoidalFuzzySetBuilder OpenRightTrapezoidal() {
        return new OpenRightTrapezoidalFuzzySetBuilder();
    }

    public static ClosedTrapezoidalFuzzySetBuilder ClosedTrapezoidal() {
        return new ClosedTrapezoidalFuzzySetBuilder();
    }

    public abstract FuzzySet build();


    public static class Boundary {
        private final double start;
        private final double end;

        public Boundary(double start, double end) {
            this.start = start;
            this.end = end;
        }
    }

    public static class BoundaryBuilder {

        private double start;

        public static BoundaryBuilder startingAt(double value) {
            BoundaryBuilder builder = new BoundaryBuilder();
            builder.start = value;
            return builder;
        }

        public Boundary endingAt(double value) {
            return new Boundary(start, value);
        }
    }

    /* Form :
       |         .........
       |        .
       |       .
       |.......
       |___________________
    */
    public static class OpenLeftTrapezoidalFuzzySetBuilder extends FuzzySetBuilder {

        private double startBoundary;

        private double endBoundary;

        public OpenLeftTrapezoidalFuzzySetBuilder withBoundary(Boundary boundary) {
            this.startBoundary = boundary.start;
            this.endBoundary = boundary.end;
            return this;
        }

        @Override
        public FuzzySet build() {
            List<Point> points = List.of(
                    new Point(minimum, 1),
                    new Point(startBoundary, 1),
                    new Point(endBoundary, 0),
                    new Point(maximum, 0)
            );
            return new FuzzySet(new LinkedList<>(points));
        }
    }


    /* Form:
       |.......
       |       .
       |        .
       |         .........
       |___________________
    */

    public static class OpenRightTrapezoidalFuzzySetBuilder extends FuzzySetBuilder {
        private double startBoundary;

        private double endBoundary;

        public OpenRightTrapezoidalFuzzySetBuilder withBoundary(Boundary boundary) {
            startBoundary = boundary.start;
            endBoundary = boundary.end;
            return this;
        }

        @Override
        public FuzzySet build() {
            List<Point> points = List.of(
                    new Point(minimum, 0),
                    new Point(startBoundary, 0),
                    new Point(endBoundary, 1),
                    new Point(maximum, 1)
            );
            return new FuzzySet(new LinkedList<>(points));
        }


    }

    /* Form :
       |       ....
       |      .    .
       |     .      .
       |.....        .....
       |___________________
    */

    public static class ClosedTrapezoidalFuzzySetBuilder extends FuzzySetBuilder {

        private double startLeftBoundary;

        private double endLeftBoundary;

        private double startRightBoundary;

        private double endRightBoundary;

        public ClosedTrapezoidalFuzzySetBuilder withLeftBoundary(Boundary boundary) {
            this.startLeftBoundary = boundary.start;
            this.endLeftBoundary = boundary.end;
            return this;
        }

        public ClosedTrapezoidalFuzzySetBuilder withRightBoundary(Boundary boundary) {
            this.startRightBoundary = boundary.start;
            this.endRightBoundary = boundary.end;
            return this;
        }

        @Override
        public FuzzySet build() {
            List<Point> points = List.of(
                    new Point(minimum, 0),
                    new Point(startLeftBoundary, 0),
                    new Point(endLeftBoundary, 1),
                    new Point(startRightBoundary, 1),
                    new Point(endRightBoundary, 0),
                    new Point(maximum, 0)
            );
            return new FuzzySet(new LinkedList<>(points));
        }
    }
}
