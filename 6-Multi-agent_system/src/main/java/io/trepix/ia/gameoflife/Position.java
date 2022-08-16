package io.trepix.ia.gameoflife;

import java.util.HashSet;
import java.util.Set;

public record Position(int x, int y) {

    public Set<Position> neighbours() {
        Set<Position> neighbours = new HashSet<>();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(i == x && j == y)) {
                    neighbours.add(new Position(i, j));
                }
            }
        }
        return neighbours;
    }

    public boolean isInsideBounds(Bounds bounds) {
        if (x < bounds.lowerWidth() || x > bounds.upperWidth()) return false;
        if (y < bounds.lowerHeight() || y > bounds.upperHeight()) return false;
        return true;
    }

}
