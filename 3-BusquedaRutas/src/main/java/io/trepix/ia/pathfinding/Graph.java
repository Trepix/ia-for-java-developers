package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;
import io.trepix.ia.pathfinding.structure.Tile;

import java.util.ArrayList;
import java.util.List;

public interface Graph<T extends Node<T>> {
    T startingNode();
    T endingNode();

    ArrayList<T> nodes();
    ArrayList<T> adjacentNodes(T origen);
    ArrayList<Arc> arcs();
    ArrayList<Arc> arcsOf(T origen);

    int numberOfNodes();
    double cost(T start, T end);

    List<Tile> pathSteps();
    void initializeEstimatedDistances();
    void clearPath();

}
