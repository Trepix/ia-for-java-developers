package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.*;

public class DepthFirstSearch<T extends Node<T>> extends PathFindingAlgorithm<T> {

    public DepthFirstSearch() {
        super("Depth (DFS)");
    }

    @Override
    protected Path<T> execute(Graph<T> graph) {
        Set<T> updatedNodes = new HashSet<>();
        Stack<T> nodesToVisit = new Stack<>();
        T start = graph.startingNode();
        nodesToVisit.push(start);
        updatedNodes.add(start);

        T end = graph.endingNode();

        boolean endReached = false;
        while (!nodesToVisit.empty() && !endReached) {
            T currentNode = nodesToVisit.pop();
            if (currentNode.equals(end)) {
                endReached = true;
            }
            for (T adjacentNode : graph.adjacentNodes(currentNode)) {
                if (!updatedNodes.contains(adjacentNode)) {
                    adjacentNode.updatePathInfo(currentNode);
                    updatedNodes.add(adjacentNode);
                    nodesToVisit.push(adjacentNode);
                }
            }
        }
        return new Path<>(graph.endingNode().pathSteps(), graph.endingNode().distanceFromStart());
    }

}
