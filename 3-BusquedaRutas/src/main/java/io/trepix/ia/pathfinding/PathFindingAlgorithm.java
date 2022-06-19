package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Tile;

import java.util.List;

public abstract class PathFindingAlgorithm {
    private final String name;

    public PathFindingAlgorithm(String name) {
        this.name = name;
    }

    public final Path findPath(Grafico grafico) {
        grafico.Eliminar();
        return execute(grafico);
    }

    public final String name() {
        return this.name;
    }

    protected abstract Path execute(Grafico grafico);

    public record Path(List<Tile> steps, double distance) {}
}
