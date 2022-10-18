package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;

public record Position(double x, double y) {

    public Position shiftInside(Bounds bounds) {
        if (this.isInside(bounds)) return this;

        var x = this.x;
        var y = this.y;
        if (x < bounds.lowerWidth()) {
            x = bounds.lowerWidth();
        }
        else if (y < bounds.lowerHeight()) {
            y = bounds.lowerHeight();
        }
        else if (x > bounds.upperWidth()) {
            x = bounds.upperWidth();
        }
        else if (y > bounds.upperHeight()) {
            y = bounds.upperHeight();
        }
        return new Position(x, y);
    }

    public double distanceTo(Position position) {
        return Math.sqrt((x- position.x)*(x- position.x)+(y-position.y)*(y-position.y));
    }

    private boolean isInside(Bounds bounds) {
        if (x < bounds.lowerWidth() || x > bounds.upperWidth()) return false;
        if (y < bounds.lowerHeight() || y > bounds.upperHeight()) return false;
        return true;
    }

    public UnitaryDirection directionTo(Position position) {
        double distance = this.distanceTo(position);
        double xDirection = (position.x() - this.x()) / distance;
        double yDirection = (position.y() - this.y()) / distance;
        return new UnitaryDirection(xDirection, yDirection);
    }

    public Position move(UnitaryDirection direction, double distance) {
        return new Position(x + direction.x() * distance, y + direction.y() * distance);
    }
}
