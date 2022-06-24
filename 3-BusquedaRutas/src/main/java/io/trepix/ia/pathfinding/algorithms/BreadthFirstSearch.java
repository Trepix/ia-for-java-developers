package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.Graph;
import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BreadthFirstSearch<T extends Node<T>> extends PathFindingAlgorithm<T> {
    
    public BreadthFirstSearch() {
        super("Breadth (BFS)");
    }
    
    @Override
    protected Path<T> execute(Graph<T> graph) {
        Set<T> updatedNodes = new HashSet<>();
        LinkedList<T> nodesToVisit = new LinkedList<>();
        T start = graph.startingNode();

        nodesToVisit.push(start);
        updatedNodes.add(start);

        T end = graph.endingNode();

        boolean endReached = false;
        while (!nodesToVisit.isEmpty() && !endReached) {
            T currentNode = nodesToVisit.removeFirst();
            if (currentNode.equals(end)) {
                endReached = true;
            }
            for (T adjacentNode : graph.adjacentNodes(currentNode)) {
                if (!updatedNodes.contains(adjacentNode)) {
                    adjacentNode.updatePathInfo(currentNode);
                    updatedNodes.add(adjacentNode);
                    nodesToVisit.add(adjacentNode);
                }
            }
        }
        return new Path<>(graph.endingNode().pathSteps(), graph.endingNode().distanceFromStart());
    }
}
