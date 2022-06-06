package io.trepix.ia.fuzzylogic;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class LinguisticVariable {
    protected String name;
    protected ArrayList<LinguisticValue> linguisticValues;
    private final double minimumValue;
    private final double maximumValue;
    public LinguisticVariable(String name, double minimum, double maximum) {
        this.name = name;
        this.minimumValue = minimum;
        this.maximumValue = maximum;
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
        return minimumValue;
    }

    public double getMaximumValue() {
        return maximumValue;
    }

}
