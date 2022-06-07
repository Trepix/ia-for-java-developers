package io.trepix.ia.fuzzylogic;

import java.util.LinkedList;
import java.util.List;

public class LinguisticVariableBuilder {

    private final String name;
    private final List<LinguisticValueBuilder> linguisticValueBuilders;
    private double minimum;
    private double maximum;

    public LinguisticVariableBuilder(String name) {
        this.name = name;
        this.linguisticValueBuilders = new LinkedList<>();
    }

    public static LinguisticVariableBuilder withName(String name) {
        return new LinguisticVariableBuilder(name);
    }

    public LinguisticVariableBuilder andLinguisticValue(LinguisticValueBuilder linguisticValueBuilder) {
        linguisticValueBuilders.add(linguisticValueBuilder);
        return this;
    }

    public LinguisticVariableBuilder withMinimumValue(double value) {
        this.minimum = value;
        return this;
    }

    public LinguisticVariableBuilder withMaximumValue(double value) {
        this.maximum = value;
        return this;
    }

    public LinguisticVariable build() {
        List<LinguisticValue> linguisticValues = linguisticValueBuilders
                .stream()
                .map(linguisticValueBuilder -> linguisticValueBuilder.changeMinimum(this.minimum))
                .map(linguisticValueBuilder -> linguisticValueBuilder.changeMaximum(this.maximum))
                .map(LinguisticValueBuilder::build)
                .toList();
        return new LinguisticVariable(name, linguisticValues);
    }



}
