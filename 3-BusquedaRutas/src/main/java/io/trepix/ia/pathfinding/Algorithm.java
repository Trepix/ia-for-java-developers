package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Tile;

import java.util.List;

public abstract class Algorithm {
    private final String name;

    public Algorithm(String name) {
        this.name = name;
    }

    public final Path findPath(Grafico grafico) {
        grafico.Eliminar();
        Grafico result = execute(grafico);
        return new Path(result.ReconstruirCamino(), result.NodoSalida().getDistanceFromBeginning());
    }

    public final String name() {
        return this.name;
    }

    protected abstract Grafico execute(Grafico grafico);

    public record Path(List<Tile> steps, double distance) {}
}
