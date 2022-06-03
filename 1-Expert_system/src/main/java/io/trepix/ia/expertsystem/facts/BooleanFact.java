package io.trepix.ia.expertsystem.facts;

import io.trepix.ia.expertsystem.Fact;

class BooleanFact extends Fact<Boolean> {
    public BooleanFact(String name, boolean value, String question, int level) {
        super(name, value, question, level);
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
