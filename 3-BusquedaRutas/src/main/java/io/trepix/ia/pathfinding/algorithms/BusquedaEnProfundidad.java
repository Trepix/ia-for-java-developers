package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Algorithm;
import io.trepix.ia.pathfinding.Grafico;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.ArrayList;
import java.util.Stack;

// Algoritmo de búsqueda en profundidad
public class BusquedaEnProfundidad extends Algorithm {

    // Constructor
    public BusquedaEnProfundidad() {
        super("Depth (DFS)");
    }
    
    // Métodos de resolución
    @Override
    protected Grafico execute(Grafico grafico) {
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
        return grafico;
    }
}