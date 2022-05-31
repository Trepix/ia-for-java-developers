package io.trepix.ia.sistemaexperto.facts;

import io.trepix.ia.sistemaexperto.Fact;

class BooleanFact implements Fact<Boolean> {

    protected String name;
    protected boolean value;
    protected int level;
    protected String question;

    // Constructor
    public BooleanFact(String name, boolean value, String question, int level) {
        this.name = name;
        this.value = value;
        this.question = question;
        this.level = level;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Boolean value() {
        return value;
    }

    @Override
    public int level() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String question() {
        return question;
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
