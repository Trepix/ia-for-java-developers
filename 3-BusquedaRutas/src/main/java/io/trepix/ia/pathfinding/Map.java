package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Tile;
import io.trepix.ia.pathfinding.structure.Tile.Cell;
import io.trepix.ia.pathfinding.structure.Tile.TileFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Map implements Graph<Tile> {

    private final HashMap<Cell,Tile> tiles;

    Tile start;
    Tile end;

    public Map(String[] map, Cell start, Cell end) {
        this.tiles = new HashMap<>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length(); j++) {
                Cell cell = new Cell(i, j);
                Tile tile = TileFactory.create(map[i].charAt(j), cell);
                tiles.put(cell, tile);
            }
        }

        this.start = tiles.get(start);
        this.start.updateDistanceFromStart(this.start.movementCost());
        this.end = tiles.get(end);
    }

    @Override
    public Tile startingNode() {
        return start;
    }

    @Override
    public Tile endingNode() {
        return end;
    }

    @Override
    public List<Tile> nodes() {
        return tiles()
                .collect(Collectors.toList());
    }

    @Override
    public List<Tile> adjacentNodes(Tile origen) {
        List<Cell> cells = origen.orthogonallyAdjacentCells();
        return cells.stream()
                .map(tiles::get)
                .filter(Objects::nonNull)
                .filter(Tile::isAccessible)
                .toList();
    }

    @Override
    public int numberOfNodes() {
        return tiles.size();
    }

    @Override
    public List<Arc<Tile>> arcsOf(Tile origen) {
        if (!origen.isAccessible()) return Collections.emptyList();

        List<Cell> cells = origen.orthogonallyAdjacentCells();
        return cells.stream()
                .map(tiles::get)
                .filter(Objects::nonNull)
                .filter(Tile::isAccessible)
                .map(destination -> new Arc<>(origen, destination, destination.movementCost()))
                .toList();
    }

    @Override
    public List<Arc<Tile>> arcs() {
        return tiles()
                .map(this::arcsOf)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private Stream<Tile> tiles() {
        return tiles.values().stream();
    }

    @Override
    public double cost(Tile start, Tile end) {
        return end.movementCost();
    }

    @Override
    public List<Tile> pathSteps() {
        Tile currentNode = end;

        LinkedList<Tile> path = new LinkedList<>();
        do {
            path.push(currentNode);
            currentNode = currentNode.getParent();
        } while (currentNode != null);
        return path;
    }

    @Override
    public void initializeEstimatedDistances() {
        tiles().forEach(tile -> tile.setManhattanDistanceTo(end));
    }

    @Override
    public void clearPath() {
        tiles().forEach(tile -> tile.setParent(null));
        tiles().forEach(tile -> tile.updateDistanceFromStart(Double.POSITIVE_INFINITY));

        start.updateDistanceFromStart(start.movementCost());
    }

}
