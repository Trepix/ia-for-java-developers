package io.trepix.ia.busquedaCaminos;

// Arc en el grafico
public class Arc {
    protected Nodo origen;
    protected Nodo destino;
    protected double cout;
    
    public Arc(Nodo _origen, Nodo _destino, double _cout) {
        origen = _origen;
        destino = _destino;
        cout = _cout;
    }
}
