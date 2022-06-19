package io.trepix.ia.pathfinding.structure;

import io.trepix.ia.pathfinding.structure.Map.Cell;

import java.security.InvalidParameterException;
import java.util.stream.Stream;

public class MapBuilder {

    private final String[] rows;
    private Cell initialTile;
    private Cell endTile;

    public MapBuilder(String[] rows) {
        this.rows = rows;
    }

    public static MapBuilder createMap(String... rows) {
        boolean allRowsHaveSameLength = Stream.of(rows).map(String::length).distinct().count() == 1;
        if (!allRowsHaveSameLength) throw new InvalidParameterException("Matrix must have the same size in all rows");

        return new MapBuilder(rows);
    }

    public MapBuilder withStartAt(int row, int column) {
        this.initialTile = new Cell(row, column);
        return this;
    }

    public MapBuilder withArrivalAt(int row, int column) {
        this.endTile = new Cell(row, column);
        return this;
    }

    public Map build() {
        return new Map(String.join("\n", rows), initialTile.row(), initialTile.column(), endTile.row(), endTile.column());
    }

}
