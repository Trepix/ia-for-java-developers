package io.trepix.ia.fuzzylogic;

public class FuzzyExpression {
    private final LinguisticVariable linguisticVariable;
    private final String linguisticValueName;

    public FuzzyExpression(LinguisticVariable linguisticVariable, String linguisticValueName) {
        this.linguisticVariable = linguisticVariable;
        this.linguisticValueName = linguisticValueName;
    }

    public ConjuntoDifuso fuzzySet() {
        return linguisticValue().fuzzySet();
    }

    public double membershipDegree(NumericalValue numericalValue) {
        return linguisticValue().membershipDegree(numericalValue.value());
    }

    private LinguisticValue linguisticValue() {
        return linguisticVariable.linguisticValue(this.linguisticValueName);
    }

    public boolean belongsTo(LinguisticVariable linguisticVariable) {
        return this.linguisticVariable.equals(linguisticVariable);
    }
}
