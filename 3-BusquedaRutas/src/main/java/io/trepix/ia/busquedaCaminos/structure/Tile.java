package io.trepix.ia.busquedaCaminos.structure;

import static io.trepix.ia.busquedaCaminos.structure.TileType.*;

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

    public static class TileFactory {

        public static Tile create(char tileChar, int row, int column) {
            return switch (tileChar) {
                case ' ' -> new Tile(Hierba, row, column);
                case '*' -> new Tile(Arbol, row, column);
                case '=' -> new Tile(Puente, row, column);
                case 'X' -> new Tile(Agua, row, column);
                case '.' -> new Tile(Camino, row, column);
                default -> null;
            };
        }

    }
}
