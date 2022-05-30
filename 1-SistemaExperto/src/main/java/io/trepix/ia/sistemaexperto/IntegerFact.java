package io.trepix.ia.sistemaexperto;
class IntegerFact implements Fact<Integer> {
    protected String name;
    @Override
    public String name() {
        return name;
    }

    protected int value;
    @Override
    public Integer value() {
        return value;
    }
    protected int level;
    @Override
    public int level() {
        return level;
    }
    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    protected String question;
    @Override
    public String question() {
        return question;
    }

    // Constructor
    public IntegerFact(String name , int value, String question, int level) {
        this.name = name ;
        this.value = value;
        this.level = level;
        this.question = question;
    }

    @Override
    public String toString() {
        return name + "=" + value + " (" + level + ")";
    }
}
