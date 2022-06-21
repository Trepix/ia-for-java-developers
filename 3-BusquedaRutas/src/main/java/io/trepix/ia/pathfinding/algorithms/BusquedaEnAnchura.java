package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.LinkedList;
import java.util.List;

// Algoritmo de búsqueda en ancho
public class BusquedaEnAnchura<T extends Node<T>> extends PathFindingAlgorithm<T> {

    // Constructor
    public BusquedaEnAnchura() {
        super("Breadth (BFS)");
    }

    // Métodos de resolución
    @Override
    protected Path<T> execute(Graph<T> graph) {
        // Creación de la lista de nodos no visitados y de la pila
        List<T> nodosNoVisitados = graph.nodes();
        LinkedList<T> nodosAVisitador = new LinkedList<>();
        nodosAVisitador.add(graph.startingNode());
        nodosNoVisitados.remove(graph.startingNode());

        // Iinicialización de la salida
        T nodeSalida = graph.endingNode();
        boolean salidaEncontrada = false;

        // Bucle principal
        while (!salidaEncontrada && nodosAVisitador.size() != 0) {
            T nodeActual = nodosAVisitador.removeFirst();
            if (nodeActual.equals(nodeSalida)) {
                // Fin del algoritmo
                salidaEncontrada = true;
            } else {
                // Se añaden los vecinos no visitados todavóa
                for (T n : graph.adjacentNodes(nodeActual)) {
                    if (nodosNoVisitados.contains(n)) {
                        nodosNoVisitados.remove(n);
                        n.setParent(nodeActual);
                        n.setDistanceFromBeginning(nodeActual.getDistanceFromBeginning() + graph.cost(nodeActual, n));
                        nodosAVisitador.add(n);
                    }
                }
            }
        }

        return new Path<>(graph.pathSteps(), graph.endingNode().getDistanceFromBeginning());
    }
}
