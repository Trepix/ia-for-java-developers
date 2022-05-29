package busquedaCaminos;

// Clase genérica que representa un algoritmo de búsqueda de caminos
// Todos los algoritmos heredados
public abstract class Algoritmo {
    protected Grafico grafico;
    protected IHM ihm;
    
    public Algoritmo(Grafico _grafico, IHM _ihm) {
        grafico = _grafico;
        ihm = _ihm;
    }
    
    public final void Resolver() {
        grafico.Eliminar();
        Run();
        ihm.MostrarResultado(grafico.ReconstruirCamino(), grafico.NodoSalida().distanciaDesdeInicio);
    }
    
    protected abstract void Run();
}
