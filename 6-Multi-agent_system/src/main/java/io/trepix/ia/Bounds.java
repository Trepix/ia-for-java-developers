package io.trepix.ia;

import java.util.List;

import static io.trepix.ia.Bounds.Bound.Name.*;
import static java.util.Comparator.comparing;

public record Bounds(int lowerWidth, int upperWidth, int lowerHeight, int upperHeight) {

    public Bound nearestTo(Position position) {
        var bounds = List.of(
                new Bound(lowerWidth, LOWER_WIDTH),
                new Bound(upperWidth, UPPER_WIDTH),
                new Bound(lowerHeight, LOWER_HEIGHT),
                new Bound(upperHeight, UPPER_HEIGHT)
        );
        return bounds.stream().min(comparing(bound -> bound.distanceTo(position))).get();
    }

    public Position shiftToNearestBound(Position position) {
        if (isInside(position)) return position;

        var x = position.x();
        var y = position.y();
        if (x < lowerWidth) {
            x = lowerWidth;
        }
        else if (y < lowerHeight) {
            y = lowerHeight;
        }
        else if (x > upperWidth) {
            x = upperWidth;
        }
        else if (y > upperHeight) {
            y = upperHeight;
        }
        return new Position(x, y);
    }

    private boolean isInside(Position position) {
        if (position.x() < lowerWidth || position.x() > upperWidth) return false;
        if (position.y() < lowerHeight || position.y() > upperHeight) return false;
        return true;
    }

    public static class Bound {

        private final int limit;
        private final Name name;

        private Bound(int limit, Name name) {
            this.limit = limit;
            this.name = name;
        }

        public Name name() {
            return this.name;
        }

        public double distanceTo(Position position) {
            return switch (name) {
                case LOWER_WIDTH -> position.x() - limit;
                case UPPER_WIDTH -> limit - position.x();
                case LOWER_HEIGHT -> position.y() - limit;
                case UPPER_HEIGHT -> limit - position.y();
            };
        }

        public enum Name {LOWER_WIDTH, UPPER_WIDTH, LOWER_HEIGHT, UPPER_HEIGHT}
    }
}
