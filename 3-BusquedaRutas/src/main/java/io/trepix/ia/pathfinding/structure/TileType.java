package io.trepix.ia.pathfinding.structure;

public enum TileType {
    Tree,
    Water,
    Grass {
        @Override
        public boolean isAccessible() {
            return true;
        }

        @Override
        public double cost() {
            return SLOWED_DOWN_MOVE;
        }
    },
    Road {
        @Override
        public boolean isAccessible() {
            return true;
        }

        @Override
        public double cost() {
            return NORMAL_MOVE;
        }
    },
    Bridge {
        @Override
        public boolean isAccessible() {
            return true;
        }

        @Override
        public double cost() {
            return SLOWED_DOWN_MOVE;
        }
    };

    private static final double UNREACHABLE = Double.POSITIVE_INFINITY;
    private static final double NORMAL_MOVE = 1;
    private static final double SLOWED_DOWN_MOVE = 2*NORMAL_MOVE;

    public boolean isAccessible() {
        return false;
    }

    public double cost() {
        return UNREACHABLE;
    }
}
