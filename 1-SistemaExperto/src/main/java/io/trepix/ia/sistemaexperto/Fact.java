package io.trepix.ia.sistemaexperto;

public interface Fact<T> {
    String name();
    T value();
    int level();
    String question();
    
    void setLevel(int level);
}
