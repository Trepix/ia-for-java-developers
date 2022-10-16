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

    private boolean isInside(Bounds bounds) {
        if (x < bounds.lowerWidth() || x > bounds.upperHeight()) return false;
        if (y < bounds.lowerHeight() || y > bounds.lowerHeight()) return false;
        return true;
    }

}
