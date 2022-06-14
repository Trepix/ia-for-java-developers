package io.trepix.ia.fuzzylogic;

import java.security.InvalidParameterException;
import java.util.LinkedList;
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
        for (LinguisticValue linguisticValue : linguisticValues) {
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

    public static class LinguisticVariableBuilder {

        private final String name;
        private final List<LinguisticValue.LinguisticValueBuilder> linguisticValueBuilders;
        private double minimum;
        private double maximum;

        public LinguisticVariableBuilder(String name) {
            this.name = name;
            this.linguisticValueBuilders = new LinkedList<>();
        }

        public static LinguisticVariableBuilder withName(String name) {
            return new LinguisticVariableBuilder(name);
        }

        public LinguisticVariableBuilder andLinguisticValue(LinguisticValue.LinguisticValueBuilder linguisticValueBuilder) {
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
                    .map(LinguisticValue.LinguisticValueBuilder::build)
                    .toList();
            return new LinguisticVariable(name, linguisticValues);
        }


    }
}
