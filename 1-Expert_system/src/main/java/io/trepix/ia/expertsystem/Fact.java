package io.trepix.ia.expertsystem;

public abstract class Fact<T> {

    protected String name;
    protected T value;
    protected int level;
    protected String question;

    protected Fact(String name, T value, String question, int level) {
        this.name = name;
        this.value = value;
        this.question = question;
        this.level = level;
    }
    public String name() {
        return this.name;
    }

    public T value() {
        return this.value;
    }

    public int level() {
        return this.level;
    }

    public String question() {
        return this.question;
    }

    public boolean requiresInput() {
        return question() != null;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
