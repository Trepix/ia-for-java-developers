package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.List;

public class AStar<T extends Node<T>> extends PathFindingAlgorithm<T> {

    public AStar() {
        super("A*");
    }

    @Override
    protected Path<T> execute(Graph<T> graph) {
        graph.initializeEstimatedDistances();
        List<T> nodes = graph.nodes();
        boolean endReached = false;

        while (!nodes.isEmpty() && !endReached) {
            T closestNodeToStart = estimatedNodeClosestToStart(nodes);

            if (closestNodeToStart.equals(graph.endingNode())) {
                endReached = true;
            }

            graph.arcsOf(closestNodeToStart)
                    .stream()
                    .filter(Arc::isShorterThanKnownPathToDestination)
                    .forEach(Arc::updatePathInfo);

            nodes.remove(closestNodeToStart);
        }

        return new Path<>(graph.pathSteps(), graph.endingNode().distanceFromStart());
    }

    private T estimatedNodeClosestToStart(List<T> nodes) {
        T closestNodeToStart = nodes.get(0);
        for (T node : nodes) {
            if (node.isEstimatedCloserToStartThan(closestNodeToStart)) {
                closestNodeToStart = node;
            }
        }
        return closestNodeToStart;
    }

}
