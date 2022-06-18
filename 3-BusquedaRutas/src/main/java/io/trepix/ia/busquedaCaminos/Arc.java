package io.trepix.ia.busquedaCaminos;

// Arc en el grafico
public class Arc {
    protected Node origen;
    protected Node destino;
    protected double cout;
    
    public Arc(Node _origen, Node _destino, double _cout) {
        origen = _origen;
        destino = _destino;
        cout = _cout;
    }
}
