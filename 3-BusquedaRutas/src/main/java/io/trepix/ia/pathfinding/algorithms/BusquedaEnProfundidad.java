package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.ArrayList;
import java.util.Stack;

// Algoritmo de búsqueda en profundidad
public class BusquedaEnProfundidad<T extends Node<T>>  extends PathFindingAlgorithm<T> {

    // Constructor
    public BusquedaEnProfundidad() {
        super("Depth (DFS)");
    }
    
    // Métodos de resolución
    @Override
    protected Path execute(Graph<T> graph) {
        // Creación de la lista de  nodos no visitados y de la pila
        ArrayList<T> nodosNoVisitados = graph.ListaNodos();
        Stack<T> nodosAVisitador = new Stack();
        nodosAVisitador.push(graph.NodoInicio());
        nodosNoVisitados.remove(graph.NodoInicio());
        
        // Iinicialización de la salida
        T nodeSalida = graph.NodoSalida();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(!salidaEncontrada && nodosAVisitador.size() != 0) {
            T nodeActual = nodosAVisitador.pop();
            if (nodeActual.equals(nodeSalida)) {
                // Se terina el algoritmo
                salidaEncontrada = true;
            }
            else {
                // Se añaden los vecinos no visitados todavía
                for (T n : graph.ListaNodosAdyacentes(nodeActual)) {
                    if (nodosNoVisitados.contains(n)) {
                        nodosNoVisitados.remove(n);
                        n.setParent(nodeActual);
                        n.setDistanceFromBeginning(nodeActual.getDistanceFromBeginning() + graph.Coste(nodeActual, n));
                        nodosAVisitador.push(n);
                    }
                }
            }
        }
        return new Path(graph.ReconstruirCamino(), graph.NodoSalida().getEstimatedDistance());
    }
}
