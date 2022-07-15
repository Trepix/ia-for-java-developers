package io.trepix.ia.geneticalgorithm;

public abstract class IndividualFactory {

    public abstract Individual create();

    public abstract Individual reproduceFrom(Individual father);

    public abstract Individual reproduceFrom(Individual father1, Individual father2);
}
