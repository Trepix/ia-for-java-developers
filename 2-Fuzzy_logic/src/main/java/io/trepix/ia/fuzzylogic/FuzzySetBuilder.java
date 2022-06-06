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
    public static class LeftTrapezoidalFuzzySetBuilder {
        private final double minimum;
        private final double maximum;

        private double startBoundary;

        private double endBoundary;

        public LeftTrapezoidalFuzzySetBuilder(double minimum, double maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public LeftTrapezoidalFuzzySetBuilder withBoundary(Boundary boundary) {
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

    public static class RightTrapezoidalFuzzySetBuilder {
        private final double minimum;
        private final double maximum;

        private double startBoundary;

        private double endBoundary;

        public RightTrapezoidalFuzzySetBuilder(double minimum, double maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public RightTrapezoidalFuzzySetBuilder withBoundary(Boundary boundary) {
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

    public static class TrapezoidalFuzzySetBuilder {
        private final double minimum;
        private final double maximum;

        private double startLeftBoundary;

        private double endLeftBoundary;

        private double startRightBoundary;

        private double endRightBoundary;

        public TrapezoidalFuzzySetBuilder(double minimum, double maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public TrapezoidalFuzzySetBuilder withLeftBoundary(Boundary boundary) {
            this.startLeftBoundary = boundary.start;
            this.endLeftBoundary = boundary.end;
            return this;
        }

        public TrapezoidalFuzzySetBuilder withRightBoundary(Boundary boundary) {
            this.startRightBoundary = boundary.start;
            this.endRightBoundary = boundary.end;
            return this;
        }

        public ConjuntoDifusoTrapecio build() {
            return new ConjuntoDifusoTrapecio(minimum, maximum, startLeftBoundary, endLeftBoundary, startRightBoundary, endRightBoundary);
        }


    }

    public static LeftTrapezoidalFuzzySetBuilder leftTrapezoidal(double minimum, double maximum) {
        return new LeftTrapezoidalFuzzySetBuilder(minimum, maximum);
    }

    public static RightTrapezoidalFuzzySetBuilder rightTrapezoidal(double minimum, double maximum) {
        return new RightTrapezoidalFuzzySetBuilder(minimum, maximum);
    }

    public static TrapezoidalFuzzySetBuilder trapezoidal(double minimum, double maximum) {
        return new TrapezoidalFuzzySetBuilder(minimum, maximum);
    }


}
