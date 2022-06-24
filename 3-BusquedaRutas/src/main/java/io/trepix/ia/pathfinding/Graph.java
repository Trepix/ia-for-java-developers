package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.List;

public interface Graph<T extends Node<T>> {
    T startingNode();

    T endingNode();

    List<T> nodes();

    List<T> adjacentNodes(T origen);

    List<Arc<T>> arcs();

    List<Arc<T>> arcsOf(T origen);

    void initializeEstimatedDistances();

    void clearPath();

}
