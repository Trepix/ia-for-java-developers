package io.trepix.ia.sistemaexperto;

public interface Fact<T> {
    String name();
    T value();
    int level();
    String question();
    default boolean isInferred() {
        return question() != null;
    }
    
    void setLevel(int level);
}
