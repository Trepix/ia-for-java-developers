package io.trepix.ia.fuzzylogic;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class LinguisticVariable {
    protected String name;
    protected ArrayList<LinguisticValue> linguisticValues;

    public LinguisticVariable(String name) {
        this.name = name;
        this.linguisticValues = new ArrayList<>();
    }
    public void addLinguisticValue(LinguisticValue linguisticValue) {
        linguisticValues.add(linguisticValue);
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

}
