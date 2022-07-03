package io.trepix.ia.algoritmogenetico;

public abstract class IndividualFactory {

    public abstract Individual create();

    public abstract Individual createFrom(Individual padre);

    public abstract Individual createFrom(Individual padre1, Individual padre2);
}
