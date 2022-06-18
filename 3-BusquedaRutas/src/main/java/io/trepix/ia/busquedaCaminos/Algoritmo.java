package io.trepix.ia.busquedaCaminos;

// Clase genérica que representa un algoritmo de búsqueda de caminos
// Todos los algoritmos heredados
public abstract class Algoritmo {
    protected Grafico grafico;
    protected IHM ihm;

    private final String name;
    
    public Algoritmo(String name, Grafico _grafico, IHM _ihm) {
        this.name = name;
        grafico = _grafico;
        ihm = _ihm;
    }
    
    public final void Resolver() {
        grafico.Eliminar();
        Run();
        ihm.showResults(grafico.ReconstruirCamino(), grafico.NodoSalida().getDistanceFromBeginning());
    }

    public final String name() {
        return this.name;
    }
    protected abstract void Run();
}
