package io.trepix.ia.fuzzylogic;

public class FuzzyExpression {
    private final LinguisticVariable linguisticVariable;
    private final LinguisticValue linguisticValue;

    public FuzzyExpression(LinguisticVariable linguisticVariable, String linguisticValueName) {
        this.linguisticVariable = linguisticVariable;
        this.linguisticValue = linguisticVariable.linguisticValue(linguisticValueName);
    }

    public FuzzySet fuzzySet() {
        return linguisticValue.fuzzySet();
    }

    public double membershipDegree(NumericalValue numericalValue) {
        return linguisticValue.membershipDegree(numericalValue.value());
    }

    public boolean belongsTo(LinguisticVariable linguisticVariable) {
        return this.linguisticVariable.equals(linguisticVariable);
    }
}
