package io.trepix.ia.fuzzylogic;

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

        public ConjuntoDifusoTrapecioIzquierda build() {
            return new ConjuntoDifusoTrapecioIzquierda(minimum, maximum, startBoundary, endBoundary);
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

        public ConjuntoDifusoTrapecioDerecha build() {
            return new ConjuntoDifusoTrapecioDerecha(minimum, maximum, startBoundary, endBoundary);
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

        public ConjuntoDifusoTrapecio build() {
            return new ConjuntoDifusoTrapecio(minimum, maximum, startLeftBoundary, endLeftBoundary, startRightBoundary, endRightBoundary);
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
