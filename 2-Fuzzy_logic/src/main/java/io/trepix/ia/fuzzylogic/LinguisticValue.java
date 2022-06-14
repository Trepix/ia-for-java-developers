package io.trepix.ia.fuzzylogic;

public class LinguisticValue {
    private final FuzzySet fuzzySet;
    private final String name;

    public LinguisticValue(String name, FuzzySet fuzzySet) {
        this.fuzzySet = fuzzySet;
        this.name = name;
    }

    boolean hasSameName(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    double membershipDegree(double value) {
        return fuzzySet.membershipDegree(value);
    }

    FuzzySet fuzzySet() {
        return fuzzySet;
    }

    public static class LinguisticValueBuilder {

        private final String name;
        private FuzzySetBuilder fuzzySetBuilder;

        public LinguisticValueBuilder(String name) {
            this.name = name;
        }

        public static LinguisticValueBuilder withName(String name) {
            return new LinguisticValueBuilder(name);
        }

        public LinguisticValueBuilder andFuzzySet(FuzzySetBuilder fuzzySetBuilder) {
            this.fuzzySetBuilder = fuzzySetBuilder;
            return this;
        }

        public LinguisticValueBuilder changeMinimum(double minimum) {
            fuzzySetBuilder = fuzzySetBuilder.changeMinimum(minimum);
            return this;
        }

        public LinguisticValueBuilder changeMaximum(double maximum) {
            fuzzySetBuilder = fuzzySetBuilder.changeMaximum(maximum);
            return this;
        }

        public LinguisticValue build() {
            return new LinguisticValue(name, fuzzySetBuilder.build());
        }
    }
}
