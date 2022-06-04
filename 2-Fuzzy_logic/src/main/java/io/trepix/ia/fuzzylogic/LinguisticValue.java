package io.trepix.ia.fuzzylogic;
public class LinguisticValue {
    protected ConjuntoDifuso fuzzySet;
    private final String name;
    public LinguisticValue(String name , ConjuntoDifuso fuzzySet) {
        this.fuzzySet = fuzzySet;
        this.name = name ;
    }

    boolean hasSameName(String name) {
        return this.name.equalsIgnoreCase(name);
    }
    double membershipDegree(double value) {
        return fuzzySet.membershipDegree(value);
    }
}
