package io.trepix.ia.busquedaCaminos;

import java.util.ArrayList;
import java.util.LinkedList;

// Algoritmo de búsqueda en ancho
public class BusquedaEnAncho extends Algoritmo {

    // Constructor
    public BusquedaEnAncho(Grafico _grafico, IHM _ihm) {
        super(_grafico,_ihm);
    }
    
    // Métodos de resolución
    @Override
    protected void Run() {
        // Creación de la lista de nodos no visitados y de la pila
        ArrayList<Nodo> nodosNoVisitados = grafico.ListaNodos();
        LinkedList<Nodo> nodosAVisitador = new LinkedList();
        nodosAVisitador.add(grafico.NodoInicio());
        nodosNoVisitados.remove(grafico.NodoInicio());
        
        // Iinicialización de la salida
        Nodo nodoSalida = grafico.NodoSalida();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(!salidaEncontrada && nodosAVisitador.size() != 0) {
            Nodo nodoActual = nodosAVisitador.removeFirst();
            if (nodoActual.equals(nodoSalida)) {
                // Fin del algoritmo
                salidaEncontrada = true;
            }
            else {
                // Se añaden los vecinos no visitados todavóa
                for (Nodo n : grafico.ListaNodosAdyacentes(nodoActual)) {
                    if (nodosNoVisitados.contains(n)) {
                        nodosNoVisitados.remove(n);
                        n.predecesor = nodoActual;
                        n.distanciaDesdeInicio = nodoActual.distanciaDesdeInicio + grafico.Cout(nodoActual, n);
                        nodosAVisitador.add(n);
                    }
                }
            }
        }
    }
}
