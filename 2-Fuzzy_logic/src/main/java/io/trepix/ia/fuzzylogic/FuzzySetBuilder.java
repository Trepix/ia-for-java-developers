package io.trepix.ia.fuzzylogic;

public class FuzzySetBuilder {

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

        public LeftTrapezoidalFuzzySetBuilder withStartBoundaryAt(double value) {
            this.startBoundary = value;
            return this;
        }

        public LeftTrapezoidalFuzzySetBuilder withEndBoundaryAt(double value) {
            this.endBoundary = value;
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

        public RightTrapezoidalFuzzySetBuilder withStartBoundaryAt(double value) {
            this.startBoundary = value;
            return this;
        }

        public RightTrapezoidalFuzzySetBuilder withEndBoundaryAt(double value) {
            this.endBoundary = value;
            return this;
        }

        public ConjuntoDifusoTrapecioDerecha build() {
            return new ConjuntoDifusoTrapecioDerecha(minimum, maximum, startBoundary, endBoundary);
        }
    }

    public static LeftTrapezoidalFuzzySetBuilder leftTrapezoidal(int minimum, int maximum) {
        return new LeftTrapezoidalFuzzySetBuilder(minimum, maximum);
    }

    public static RightTrapezoidalFuzzySetBuilder rightTrapezoidal(int minimum, int maximum) {
        return new RightTrapezoidalFuzzySetBuilder(minimum, maximum);
    }

}
