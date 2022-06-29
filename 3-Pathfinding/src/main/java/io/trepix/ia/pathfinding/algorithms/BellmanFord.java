package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.List;

public class BellmanFord<T extends Node<T>> extends PathFindingAlgorithm<T> {

    public BellmanFord() {
        super("Bellman-Ford");
    }

    @Override
    protected Path<T> execute(Graph<T> graph) {
        List<Arc<T>> arcs = graph.arcs();

        int maxNodesToVisit = graph.nodes().size() - 1;
        boolean foundShortestPath = true;
        for (int visitedNodes=0; visitedNodes < maxNodesToVisit && foundShortestPath; visitedNodes++) {
            foundShortestPath = false;
            for (Arc<T> arc : arcs) {
                if (arc.isShorterThanKnownPathToDestination()) {
                    arc.updatePathInfo();
                    foundShortestPath = true;
                }
            }
        }
        checkIfThereAreNegativeLoops(arcs);

        return new Path<>(graph.endingNode().pathSteps(), graph.endingNode().distanceFromStart());
    }

    private void checkIfThereAreNegativeLoops(List<Arc<T>> arcs) {
        for (Arc<T> arc : arcs) {
            if (arc.isShorterThanKnownPathToDestination()) {
                System.err.println("Negative loop - There is no shorter way");
            }
        }
    }
}
