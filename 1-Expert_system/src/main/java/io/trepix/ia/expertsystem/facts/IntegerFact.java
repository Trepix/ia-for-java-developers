package io.trepix.ia.expertsystem.facts;

import io.trepix.ia.expertsystem.Fact;

class IntegerFact extends Fact<Integer> {

    public IntegerFact(String name, int value, String question, int level) {
        super(name, value, question, level);
    }

    public IntegerFact(Fact<?> fact, int value, String question, int level) {
        super(fact, value, question, level);
    }

    @Override
    public String toString() {
        return name + "=" + value + " (" + level + ")";
    }
}
