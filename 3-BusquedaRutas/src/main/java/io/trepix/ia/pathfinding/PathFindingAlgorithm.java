package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Node;
import io.trepix.ia.pathfinding.structure.Tile;

import java.util.List;

public abstract class PathFindingAlgorithm<T extends Node<T>> {
    private final String name;

    public PathFindingAlgorithm(String name) {
        this.name = name;
    }

    public final Path findPath(Graph<T> graph) {
        graph.clearPath();
        return execute(graph);
    }

    public final String name() {
        return this.name;
    }

    protected abstract Path execute(Graph<T> graph);

    public record Path(List<Tile> steps, double distance) {
    }
}
