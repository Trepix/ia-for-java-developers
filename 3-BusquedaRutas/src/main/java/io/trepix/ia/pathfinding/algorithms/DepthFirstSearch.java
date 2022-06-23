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

        while (!nodesToVisit.empty()) {
            T currentNode = nodesToVisit.pop();
            if (currentNode.equals(end)) {
                break;
            }

            for (T node : graph.adjacentNodes(currentNode)) {
                if (!updatedNodes.contains(node)) {
                    node.updatePathInfo(currentNode);
                    updatedNodes.add(node);
                    nodesToVisit.push(node);
                }
            }
        }
        return new Path<>(graph.pathSteps(), graph.endingNode().distanceFromStart());
    }

}
