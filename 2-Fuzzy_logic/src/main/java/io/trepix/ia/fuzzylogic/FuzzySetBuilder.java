package io.trepix.ia.fuzzylogic;

public class FuzzySetBuilder {

    /* Form :
      _____
           \
            \
             \_________
    */
    public static class LeftTrapezoidalFuzzySet {
        private final int minimum;
        private final int maximum;

        private int startBoundary;

        private int endBoundary;

        public LeftTrapezoidalFuzzySet(int minimum, int maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public LeftTrapezoidalFuzzySet withStartBoundaryAt(int value) {
            this.startBoundary = value;
            return this;
        }

        public LeftTrapezoidalFuzzySet withEndBoundaryAt(int value) {
            this.endBoundary = value;
            return this;
        }

        public ConjuntoDifusoTrapecioIzquierda build() {
            return new ConjuntoDifusoTrapecioIzquierda(minimum, maximum, startBoundary, endBoundary);
        }
    }

    public static LeftTrapezoidalFuzzySet leftTrapezoidal(int minimum, int maximum) {
        return new LeftTrapezoidalFuzzySet(minimum, maximum);
    }

}
