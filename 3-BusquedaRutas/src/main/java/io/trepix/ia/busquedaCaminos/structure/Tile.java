package io.trepix.ia.busquedaCaminos.structure;

public class Tile extends Node {
    private final TileType type;
    private final int row;
    private final int column;
    public Tile(TileType type, int row, int column) {
        this.type = type;
        this.row = row;
        this.column = column;
    }
    public boolean isAccessible() {
        return type.isAccessible();
    }
    public double movementCost() {
        return type.cost();
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    @Override
    public String toString() {
        return "[" + row + ";" + column + ";" + type.toString() + "]";
    }
}
