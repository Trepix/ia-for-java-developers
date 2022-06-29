package io.trepix.ia.pathfinding.structure;

import java.util.List;

import static io.trepix.ia.pathfinding.structure.TileType.*;

public class Tile extends Node<Tile> {
    private final TileType type;
    private final Cell cell;

    public Tile(Cell cell, TileType type) {
        this.type = type;
        this.cell = cell;
    }


    public boolean isAccessible() {
        return type.isAccessible();
    }

    @Override
    public double movementCost() {
        return type.cost();
    }

    public void setManhattanDistanceTo(Tile tile) {
        this.setEstimatedDistance(this.cell.manhattanDistanceTo(tile.cell));
    }

    @Override
    public String toString() {
        return "[" + cell.row() + ";" + cell.column() + ";" + type.toString() + "]";
    }

    public List<Cell> orthogonallyAdjacentCells() {
        return cell.orthogonallyAdjacentCells();
    }

    public static class TileFactory {

        public static Tile create(char tileChar, Cell cell) {
            return switch (tileChar) {
                case ' ' -> new Tile(cell, Grass);
                case '*' -> new Tile(cell, Tree);
                case '=' -> new Tile(cell, Bridge);
                case 'X' -> new Tile(cell, Water);
                case '.' -> new Tile(cell, Road);
                default -> null;
            };
        }

    }

    public record Cell(int row, int column) {
        public List<Cell> orthogonallyAdjacentCells() {
            return List.of(
                    new Cell(row, column - 1),
                    new Cell(row, column + 1),
                    new Cell(row - 1, column),
                    new Cell(row + 1, column)
            );
        }

        public double manhattanDistanceTo(Cell cell) {
            return Math.abs(this.row() - cell.row) + Math.abs(this.column - cell.column);
        }
    }
}
