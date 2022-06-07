package io.trepix.ia.fuzzylogic;

public class LinguisticValueBuilder {

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

    public LinguisticValue build() {
        return new LinguisticValue(name, fuzzySetBuilder.build());
    }
}
