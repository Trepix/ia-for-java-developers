package io.trepix.ia.pathfinding.structure;

import static io.trepix.ia.pathfinding.structure.TileType.*;

public class Tile extends Node<Tile> {
    private final TileType type;

    private final Cell cell;

    public Tile(TileType type, int row, int column) {
        this.type = type;
        cell = new Cell(row, column);
    }

    public boolean isAccessible() {
        return type.isAccessible();
    }

    public double movementCost() {
        return type.cost();
    }

    public int row() {
        return cell.row();
    }

    public int column() {
        return cell.column();
    }

    @Override
    public String toString() {
        return "[" + cell.row() + ";" + cell.column() + ";" + type.toString() + "]";
    }

    public static class TileFactory {

        public static Tile create(char tileChar, int row, int column) {
            return switch (tileChar) {
                case ' ' -> new Tile(Grass, row, column);
                case '*' -> new Tile(Tree, row, column);
                case '=' -> new Tile(Bridge, row, column);
                case 'X' -> new Tile(Water, row, column);
                case '.' -> new Tile(Road, row, column);
                default -> null;
            };
        }

    }

    public record Cell(int row, int column) {
    }
}
