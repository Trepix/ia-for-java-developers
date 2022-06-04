package io.trepix.ia.fuzzylogic;

public class NumericalValue {
    private final LinguisticVariable linguisticVariable;
    private final double value;

    public NumericalValue(LinguisticVariable linguisticVariable, double value) {
        this.linguisticVariable = linguisticVariable;
        this.value = value;
    }
    public double value() {
        return value;
    }

    boolean belongsTo(ExpresionDifusa fuzzyExpression) {
        return fuzzyExpression.varL.equals(linguisticVariable);
    }
}
