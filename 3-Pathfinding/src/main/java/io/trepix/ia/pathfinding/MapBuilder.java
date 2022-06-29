package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Tile.Cell;

import java.security.InvalidParameterException;
import java.util.stream.Stream;

public class MapBuilder {

    private final String[] rows;
    private Cell start;
    private Cell end;

    public MapBuilder(String[] rows) {
        this.rows = rows;
    }

    public static MapBuilder createMap(String... rows) {
        boolean allRowsHaveSameLength = Stream.of(rows).map(String::length).distinct().count() == 1;
        if (!allRowsHaveSameLength) throw new InvalidParameterException("Matrix must have the same size in all rows");

        return new MapBuilder(rows);
    }

    public MapBuilder withStartAt(int row, int column) {
        this.start = new Cell(row, column);
        return this;
    }

    public MapBuilder withArrivalAt(int row, int column) {
        this.end = new Cell(row, column);
        return this;
    }

    public Map build() {
        return new Map(rows, start, end);
    }

}
