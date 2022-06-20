package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.List;

// Algoritmo de Dijkstra
public class Dijkstra<T extends Node<T>> extends PathFindingAlgorithm<T> {

    // Constructor
    public Dijkstra() {
        super("Dijkstra");
    }

    // Métodos principal
    @Override
    protected Path execute(Graph<T> graph) {
        // Iinicialización
        List<T> listaNodes = graph.nodes();
        boolean salidaEncontrada = false;

        // Bucle principal
        while (listaNodes.size() != 0 && !salidaEncontrada) {
            // Búsqueda del nodo con la distancia la más baja
            T nodeActual = listaNodes.get(0);
            for (T node : listaNodes) {
                if (node.getDistanceFromBeginning() < nodeActual.getDistanceFromBeginning()) {
                    nodeActual = node;
                }
            }

            if (nodeActual.equals(graph.endingNode())) {
                salidaEncontrada = true;
            } else {
                // Se aplica los arco salientes de este nodo
                List<Arc<T>> arcosSalientes = graph.arcsOf(nodeActual);

                for (Arc arc : arcosSalientes) {
                    if (arc.origin().getDistanceFromBeginning() + arc.cost() < arc.destination().getDistanceFromBeginning()) {
                        arc.destination().setDistanceFromBeginning(arc.origin().getDistanceFromBeginning() + arc.cost());
                        arc.destination().setParent(arc.origin());
                    }
                }

                listaNodes.remove(nodeActual);
            }
        }

        return new Path(graph.pathSteps(), graph.endingNode().getEstimatedDistance());
    }

}
