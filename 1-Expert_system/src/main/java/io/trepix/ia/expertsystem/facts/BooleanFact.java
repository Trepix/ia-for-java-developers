package io.trepix.ia.expertsystem.facts;

import io.trepix.ia.expertsystem.Fact;

class BooleanFact extends Fact<Boolean> {
    public BooleanFact(String name, boolean value, String question, int level) {
        super(name, value, question, level);
    }

    public BooleanFact(Fact<?> fact, boolean value, String question, int level) {
        super(fact, value, question, level);
    }

    @Override
    public String toString() {
        String res = "";
        if (!value) {
            res += "!";
        }
        res += name + " (" + level + ")";
        return res;
    }
}
