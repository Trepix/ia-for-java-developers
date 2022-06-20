package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.ArrayList;

// Algoritmo A*
public class AStar<T extends Node<T>> extends PathFindingAlgorithm<T> {

    // Constructor
    public AStar() {
        super("A*");
    }
    
    // Métodos principal
    @Override
    protected Path execute(Graph<T> graph) {
        // Iinicialización
        graph.initializeEstimatedDistances();
        ArrayList<T> listaNodes = graph.nodes();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(listaNodes.size() != 0 && !salidaEncontrada) {
            // Búsqueda del nodo con la distancia más baja
            T nodeActual = listaNodes.get(0);
            for (T node : listaNodes) {
                if (node.getDistanceFromBeginning() + node.getEstimatedDistance() < nodeActual.getDistanceFromBeginning() + nodeActual.getEstimatedDistance()) {
                    nodeActual = node;
                }
            }
            
            if (nodeActual.equals(graph.endingNode())) {
                // Encontrada la salida
                salidaEncontrada = true;
            }
            else {
                // Se aplican los arcos salientes de este nodo
                ArrayList<Arc> arcosSalientes = graph.arcsOf(nodeActual);
                
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
