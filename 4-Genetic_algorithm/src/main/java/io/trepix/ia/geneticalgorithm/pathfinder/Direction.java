package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.pathfinder.Labyrinth.Displacement;

import java.util.Random;

public enum Direction {
    Up {
        @Override
        public Displacement displacement() {
            return new Displacement(-1, 0);
        }
    }, Down {
        @Override
        public Displacement displacement() {
            return new Displacement(1, 0);
        }
    }, Left {
        @Override
        public Displacement displacement() {
            return new Displacement(0, -1);
        }
    }, Right {
        @Override
        public Displacement displacement() {
            return new Displacement(0, 1);
        }
    };

    public static Direction pickOne(Random random) {
        return values()[random.nextInt(values().length)];
    }

    public abstract Displacement displacement();

    @Override
    public String toString() {
        return name().substring(0, 1);
    }
}
