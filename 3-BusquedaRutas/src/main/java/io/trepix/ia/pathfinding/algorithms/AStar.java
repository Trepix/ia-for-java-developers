package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.List;

// Algoritmo A*
public class AStar<T extends Node<T>> extends PathFindingAlgorithm<T> {

    // Constructor
    public AStar() {
        super("A*");
    }

    // Métodos principal
    @Override
    protected Path<T> execute(Graph<T> graph) {
        // Iinicialización
        graph.initializeEstimatedDistances();
        List<T> listaNodes = graph.nodes();
        boolean salidaEncontrada = false;

        // Bucle principal
        while (listaNodes.size() != 0 && !salidaEncontrada) {
            // Búsqueda del nodo con la distancia más baja
            T nodeActual = listaNodes.get(0);
            for (T node : listaNodes) {
                if (node.distanceFromStart() + node.getEstimatedDistance() < nodeActual.distanceFromStart() + nodeActual.getEstimatedDistance()) {
                    nodeActual = node;
                }
            }

            if (nodeActual.equals(graph.endingNode())) {
                // Encontrada la salida
                salidaEncontrada = true;
            } else {
                // Se aplican los arcos salientes de este nodo
                List<Arc<T>> arcosSalientes = graph.arcsOf(nodeActual);

                for (Arc<T> arc : arcosSalientes) {
                    if (arc.origin().distanceFromStart() + arc.cost() < arc.destination().distanceFromStart()) {
                        arc.destination().updateDistanceFromStart(arc.origin().distanceFromStart() + arc.cost());
                        arc.destination().setParent(arc.origin());
                    }
                }

                listaNodes.remove(nodeActual);
            }
        }

        return new Path<>(graph.pathSteps(), graph.endingNode().distanceFromStart());
    }
}
