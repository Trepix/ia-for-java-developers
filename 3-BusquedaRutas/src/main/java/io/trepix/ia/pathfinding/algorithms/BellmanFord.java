package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.ArrayList;

// Algoritmo de Bellman-Ford
public class BellmanFord<T extends Node<T>>  extends PathFindingAlgorithm<T> {
    // Constructor
    public BellmanFord() {
        super("Bellman-Ford");
    }

    // Métodos de resolución
    @Override
    protected Path execute(Graph<T> graph) {
        // Iinicialización
        boolean distanciaCambiada = true;
        int i = 0;
        ArrayList<Arc> listaArcos = graph.ListaArcos();
        
        // Bucle principal
        int numBucleMax = graph.NumeroNodos() - 1;
        while (i < numBucleMax && distanciaCambiada) {
            distanciaCambiada = false;
            for (Arc arc : listaArcos) {
                if (arc.origin().getDistanceFromBeginning() + arc.cost() < arc.destination().getDistanceFromBeginning()) {
                    // Encontrado un camino más corto
                    arc.destination().setDistanceFromBeginning(arc.origin().getDistanceFromBeginning() + arc.cost());
                    arc.destination().setParent(arc.origin());
                    distanciaCambiada = true;
                }
            }
            i++;
        }
        
        // Prueba si el bucle es negativo
        for (Arc arc : listaArcos) {
            if (arc.origin().getDistanceFromBeginning() + arc.cost() < arc.destination().getDistanceFromBeginning()) {
                System.err.println("Bucle negativo - sin camino más corto");
            }
        }

        return new Path(graph.ReconstruirCamino(), graph.NodoSalida().getEstimatedDistance());
    }
}
