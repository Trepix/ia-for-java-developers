package busquedaCaminos;

import java.util.ArrayList;

// Algoritmo A*
public class AStar extends Algoritmo {

    // Constructor
    public AStar(Grafico _grafico, IHM _ihm) {
        super(_grafico, _ihm);
    }
    
    // Métodos principal
    @Override
    protected void Run() {
        // Iinicialización
        grafico.CalcularDistanciasEstimadas();
        ArrayList<Nodo> listaNodos = grafico.ListaNodos();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(listaNodos.size() != 0 && !salidaEncontrada) {
            // Búsqueda del nodo con la distancia más baja
            Nodo nodoActual = listaNodos.get(0);
            for (Nodo nodo : listaNodos) {
                if (nodo.distanciaDesdeInicio + nodo.distanciaEstimada < nodoActual.distanciaDesdeInicio + nodoActual.distanciaEstimada) {
                    nodoActual = nodo;
                }
            }
            
            if (nodoActual.equals(grafico.NodoSalida())) {
                // Encontrada la salida
                salidaEncontrada = true;
            }
            else {
                // Se aplican los arcos salientes de este nodo
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
