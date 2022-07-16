package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.pathfinder.Labyrinth.Displacement;

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

    public abstract Displacement displacement();
}
