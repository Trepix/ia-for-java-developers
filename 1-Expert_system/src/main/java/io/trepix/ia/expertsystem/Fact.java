package io.trepix.ia.expertsystem;

public interface Fact<T> {
    String name();

    T value();

    int level();

    String question();

    default boolean requiresInput() {
        return question() != null;
    }

    void setLevel(int level);
}
