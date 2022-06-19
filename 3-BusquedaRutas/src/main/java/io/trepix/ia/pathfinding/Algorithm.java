package io.trepix.ia.pathfinding;

public abstract class Algorithm {
    private final String name;

    public Algorithm(String name) {
        this.name = name;
    }

    public final Grafico findPath(Grafico grafico) {
        grafico.Eliminar();
        return execute(grafico);
    }

    public final String name() {
        return this.name;
    }

    protected abstract Grafico execute(Grafico grafico);
}
