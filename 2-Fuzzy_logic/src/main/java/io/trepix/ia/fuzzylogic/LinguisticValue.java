package io.trepix.ia.fuzzylogic;
public class LinguisticValue {
    private final FuzzySet fuzzySet;
    private final String name;
    public LinguisticValue(String name , FuzzySet fuzzySet) {
        this.fuzzySet = fuzzySet;
        this.name = name ;
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
}
