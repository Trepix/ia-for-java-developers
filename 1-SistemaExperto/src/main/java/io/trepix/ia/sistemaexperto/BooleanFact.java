package io.trepix.ia.sistemaexperto;

class BooleanFact implements Fact<Boolean> {

    protected String name;
    @Override
    public String name() {
        return name;
    }

    protected boolean value;
    @Override
    public Boolean value() {
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
    public BooleanFact(String name , boolean value, String question, int level) {
        this.name = name ;
        this.value = value;
        this.question = question;
        this.level = level;
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
