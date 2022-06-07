package io.trepix.ia.fuzzylogic;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

public class LinguisticVariable {
    protected String name;
    protected List<LinguisticValue> linguisticValues;

    public LinguisticVariable(String name, List<LinguisticValue> linguisticValues) {
        this.name = name;
        this.linguisticValues = linguisticValues;
    }

    LinguisticValue linguisticValue(String name) {
        for(LinguisticValue linguisticValue : linguisticValues) {
            if (linguisticValue.hasSameName(name)) {
                return linguisticValue;
            }
        }
        throw new InvalidParameterException();
    }

    public double getMinimumValue() {
        return linguisticValues.stream()
                .map(LinguisticValue::fuzzySet)
                .map(FuzzySet::minimum)
                .min(Double::compare)
                .orElseThrow();
    }

    public double getMaximumValue() {
        return linguisticValues.stream()
                .map(LinguisticValue::fuzzySet)
                .map(FuzzySet::maximum)
                .max(Double::compare)
                .orElseThrow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinguisticVariable that = (LinguisticVariable) o;
        return Objects.equals(name, that.name) && Objects.equals(linguisticValues, that.linguisticValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, linguisticValues);
    }
}
