package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.List;

public class Dijkstra<T extends Node<T>> extends PathFindingAlgorithm<T> {

    public Dijkstra() {
        super("Dijkstra");
    }

    @Override
    protected Path<T> execute(Graph<T> graph) {
        List<T> nodes = graph.nodes();
        boolean endReached = false;

        while (!nodes.isEmpty() && !endReached) {
            T closerNodeToStart = getCloserNodeToStart(nodes);
            if (closerNodeToStart.equals(graph.endingNode())) {
                endReached = true;
            }

            graph.arcsOf(closerNodeToStart)
                    .stream()
                    .filter(Arc::isShorterThanKnownPathToDestination)
                    .forEach(Arc::updatePathInfo);

            nodes.remove(closerNodeToStart);
        }

        return new Path<>(graph.pathSteps(), graph.endingNode().distanceFromStart());
    }

    private T getCloserNodeToStart(List<T> nodes) {
        T closestNodeToStart = nodes.get(0);
        for (T node : nodes) {
            if (node.isCloserToStartThan(closestNodeToStart)) {
                closestNodeToStart = node;
            }
        }
        return closestNodeToStart;
    }

}
