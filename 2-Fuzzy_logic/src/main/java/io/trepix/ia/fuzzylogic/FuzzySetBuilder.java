package io.trepix.ia.fuzzylogic;

import java.util.List;

public class FuzzySetBuilder {

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
    public static class OpenLeftTrapezoidalFuzzySetBuilder {
        private final double minimum;
        private final double maximum;

        private double startBoundary;

        private double endBoundary;

        public OpenLeftTrapezoidalFuzzySetBuilder(double minimum, double maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public OpenLeftTrapezoidalFuzzySetBuilder withBoundary(Boundary boundary) {
            this.startBoundary = boundary.start;
            this.endBoundary = boundary.end;
            return this;
        }

        public FuzzySet build() {
            List<Punto2D> points = List.of(
                    new Punto2D(minimum, 1),
                    new Punto2D(startBoundary, 1),
                    new Punto2D(endBoundary, 0),
                    new Punto2D(maximum, 0)
            );
            return new FuzzySet(minimum, maximum, points);
        }
    }


    /* Form:
       |.......
       |       .
       |        .
       |         .........
       |___________________
    */

    public static class OpenRightTrapezoidalFuzzySetBuilder {
        private final double minimum;
        private final double maximum;

        private double startBoundary;

        private double endBoundary;

        public OpenRightTrapezoidalFuzzySetBuilder(double minimum, double maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public OpenRightTrapezoidalFuzzySetBuilder withBoundary(Boundary boundary) {
            startBoundary = boundary.start;
            endBoundary = boundary.end;
            return this;
        }

        public FuzzySet build() {
            List<Punto2D> points = List.of(
                    new Punto2D(minimum, 0),
                    new Punto2D(startBoundary, 0),
                    new Punto2D(endBoundary, 1),
                    new Punto2D(maximum, 1)
            );
            return new FuzzySet(minimum, maximum, points);
        }


    }

    /* Form :
       |       ....
       |      .    .
       |     .      .
       |.....        .....
       |___________________
    */

    public static class ClosedTrapezoidalFuzzySetBuilder {
        private final double minimum;
        private final double maximum;

        private double startLeftBoundary;

        private double endLeftBoundary;

        private double startRightBoundary;

        private double endRightBoundary;

        public ClosedTrapezoidalFuzzySetBuilder(double minimum, double maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

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

        public FuzzySet build() {
            List<Punto2D> points = List.of(
                new Punto2D(minimum, 0),
                new Punto2D(startLeftBoundary, 0),
                new Punto2D(endLeftBoundary, 1),
                new Punto2D(startRightBoundary, 1),
                new Punto2D(endRightBoundary, 0),
                new Punto2D(maximum, 0)
            );
            return new FuzzySet(minimum, maximum, points);
        }


    }

    public static OpenLeftTrapezoidalFuzzySetBuilder openLeftTrapezoidal(double minimum, double maximum) {
        return new OpenLeftTrapezoidalFuzzySetBuilder(minimum, maximum);
    }

    public static OpenRightTrapezoidalFuzzySetBuilder openRightTrapezoidal(double minimum, double maximum) {
        return new OpenRightTrapezoidalFuzzySetBuilder(minimum, maximum);
    }

    public static ClosedTrapezoidalFuzzySetBuilder closedTrapezoidal(double minimum, double maximum) {
        return new ClosedTrapezoidalFuzzySetBuilder(minimum, maximum);
    }


}
