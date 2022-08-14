package io.trepix.ia.gameoflife;

import java.util.Random;

public class StartConfig {

    private final Random random;

    private final double density;

    public StartConfig(Random random, double density) {
        this.random = random;
        this.density = density;
    }

    public Random generator() {
        return random;
    }

    public double density() {
        return density;
    }

}
