package io.trepix.ia.expertsystem;

public abstract class Fact<T> implements Comparable<Fact<?>>{

    protected final String name;
    protected final T value;
    protected int level;
    protected final String question;

    protected Fact(String name, T value, String question, int level) {
        this.name = name;
        this.value = value;
        this.question = question;
        this.level = level;
    }

    protected Fact(Fact<?> fact, T value, String question, int level) {
        this(fact.name, value, question, level);
    }
    public boolean isTheSame(Fact<?> fact) {
        return this.name.equals(fact.name);
    }

    public boolean hasSameValue(Fact<?> fact) {
        return this.value.equals(fact.value);
    }
    public int level() {
        return this.level;
    }
    public String question() {
        return this.question;
    }

    public boolean requiresInput() {
        return question != null;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(Fact<?> fact) {
        return Integer.compare(fact.level(), this.level());
    }
}
