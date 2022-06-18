package io.trepix.ia.busquedaCaminos;

import io.trepix.ia.busquedaCaminos.structure.Node;

import java.util.ArrayList;
import java.util.Stack;

// Algoritmo de búsqueda en profundidad
public class BusquedaEnProfundidad extends Algoritmo {

    // Constructor
    public BusquedaEnProfundidad(Grafico _grafico, IHM _ihm) {
        super(_grafico,_ihm);
    }
    
    // Métodos de resolución
    @Override
    protected void Run() {
        // Creación de la lista de  nodos no visitados y de la pila
        ArrayList<Node> nodosNoVisitados = grafico.ListaNodos();
        Stack<Node> nodosAVisitador = new Stack();
        nodosAVisitador.push(grafico.NodoInicio());
        nodosNoVisitados.remove(grafico.NodoInicio());
        
        // Iinicialización de la salida
        Node nodeSalida = grafico.NodoSalida();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(!salidaEncontrada && nodosAVisitador.size() != 0) {
            Node nodeActual = nodosAVisitador.pop();
            if (nodeActual.equals(nodeSalida)) {
                // Se terina el algoritmo
                salidaEncontrada = true;
            }
            else {
                // Se añaden los vecinos no visitados todavía
                for (Node n : grafico.ListaNodosAdyacentes(nodeActual)) {
                    if (nodosNoVisitados.contains(n)) {
                        nodosNoVisitados.remove(n);
                        n.setParent(nodeActual);
                        n.setDistanceFromBeginning(nodeActual.getDistanceFromBeginning() + grafico.Coste(nodeActual, n));
                        nodosAVisitador.push(n);
                    }
                }
            }
        }
    }
}
