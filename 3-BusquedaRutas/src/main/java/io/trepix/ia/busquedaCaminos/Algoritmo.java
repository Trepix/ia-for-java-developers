package io.trepix.ia.busquedaCaminos;

// Clase genérica que representa un algoritmo de búsqueda de caminos
// Todos los algoritmos heredados
public abstract class Algoritmo {
    private final String name;
    public Algoritmo(String name) {
        this.name = name;
    }
    
    public final Grafico findPath(Grafico grafico) {
        grafico.Eliminar();
        return Run(grafico);
    }

    public final String name() {
        return this.name;
    }
    protected abstract Grafico Run(Grafico grafico);
}
