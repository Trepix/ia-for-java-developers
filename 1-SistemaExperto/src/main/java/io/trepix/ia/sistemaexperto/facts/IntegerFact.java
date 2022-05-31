package io.trepix.ia.sistemaexperto.facts;

import io.trepix.ia.sistemaexperto.Fact;

class IntegerFact implements Fact<Integer> {
    protected String name;
    protected int value;
    protected int level;
    protected String question;

    // Constructor
    public IntegerFact(String name, int value, String question, int level) {
        this.name = name;
        this.value = value;
        this.level = level;
        this.question = question;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Integer value() {
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
        return name + "=" + value + " (" + level + ")";
    }
}
