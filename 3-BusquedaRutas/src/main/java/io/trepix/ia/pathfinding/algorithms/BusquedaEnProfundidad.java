package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.List;
import java.util.Stack;

// Algoritmo de búsqueda en profundidad
public class BusquedaEnProfundidad<T extends Node<T>> extends PathFindingAlgorithm<T> {

    // Constructor
    public BusquedaEnProfundidad() {
        super("Depth (DFS)");
    }

    // Métodos de resolución
    @Override
    protected Path<T> execute(Graph<T> graph) {
        // Creación de la lista de  nodos no visitados y de la pila
        List<T> nodosNoVisitados = graph.nodes();
        Stack<T> nodosAVisitador = new Stack<>();
        nodosAVisitador.push(graph.startingNode());
        nodosNoVisitados.remove(graph.startingNode());

        // Iinicialización de la salida
        T nodeSalida = graph.endingNode();
        boolean salidaEncontrada = false;

        // Bucle principal
        while (!salidaEncontrada && nodosAVisitador.size() != 0) {
            T nodeActual = nodosAVisitador.pop();
            if (nodeActual.equals(nodeSalida)) {
                // Se terina el algoritmo
                salidaEncontrada = true;
            } else {
                // Se añaden los vecinos no visitados todavía
                for (T n : graph.adjacentNodes(nodeActual)) {
                    if (nodosNoVisitados.contains(n)) {
                        nodosNoVisitados.remove(n);
                        n.setParent(nodeActual);
                        n.setDistanceFromBeginning(nodeActual.getDistanceFromBeginning() + graph.cost(nodeActual, n));
                        nodosAVisitador.push(n);
                    }
                }
            }
        }
        return new Path<>(graph.pathSteps(), graph.endingNode().getDistanceFromBeginning());
    }
}
