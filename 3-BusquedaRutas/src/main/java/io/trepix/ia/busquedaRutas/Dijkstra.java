package busquedaCaminos;

import java.util.ArrayList;

// Algoritmo de Dijkstra
public class Dijkstra extends Algoritmo {

    // Constructor
    public Dijkstra(Grafico _grafico, IHM _ihm) {
        super(_grafico, _ihm);
    }
    
    // Métodos principal
    @Override
    protected void Run() {
        // Iinicialización
        ArrayList<Nodo> listaNodos = grafico.ListaNodos();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(listaNodos.size() != 0 && !salidaEncontrada) {
            // Búsqueda del nodo con la distancia la más baja
            Nodo nodoActual = listaNodos.get(0);
            for (Nodo nodo : listaNodos) {
                if (nodo.distanciaDesdeInicio < nodoActual.distanciaDesdeInicio) {
                    nodoActual = nodo;
                }
            }
            
            if (nodoActual.equals(grafico.NodoSalida())) {
                salidaEncontrada = true;
            }
            else {
                // Se aplica los arco salientes de este nodo
                ArrayList<Arc> arcosSalientes = grafico.ListaArcosSalientes(nodoActual);
                
                for (Arc arc : arcosSalientes) {
                    if (arc.origen.distanciaDesdeInicio + arc.cout < arc.destino.distanciaDesdeInicio) {
                        arc.destino.distanciaDesdeInicio = arc.origen.distanciaDesdeInicio + arc.cout;
                        arc.destino.predecesor = arc.origen;
                    }
                }
                
                listaNodos.remove(nodoActual);
            }
        }
    }
    
}
