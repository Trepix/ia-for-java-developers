package io.trepix.ia.Logicadifusa;

import java.util.ArrayList;
import java.util.Optional;

public class LinguisticVariable {
    protected String name;
    protected ArrayList<LinguisticValue> linguisticValues;
    protected double minimumValue;
    protected double maximumValue;
    public LinguisticVariable(String name, double minimum, double maximum) {
        this.name = name;
        this.minimumValue = minimum;
        this.maximumValue = maximum;
        this.linguisticValues = new ArrayList<>();
    }
    public void addLinguisticValue(LinguisticValue linguisticValue) {
        linguisticValues.add(linguisticValue);
    }
    Optional<LinguisticValue> searchLinguisticValue(String name) {
        for(LinguisticValue linguisticValue : linguisticValues) {
            if (linguisticValue.hasSameName(name)) {
                return Optional.of(linguisticValue);
            }
        }
        return Optional.empty();
    }
}
