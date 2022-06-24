package io.trepix.ia.pathfinding.structure;

import java.util.LinkedList;
import java.util.List;

public abstract class Node<T extends Node<T>> {
    private T parent = null;
    private double distanceFromBeginning = Double.POSITIVE_INFINITY;
    private double estimatedDistance;

    protected T getParent() {
        return parent;
    }

    public double distanceFromStart() {
        return distanceFromBeginning;
    }

    public void updateDistanceFromStart(double distanceFromBeginning) {
        this.distanceFromBeginning = distanceFromBeginning;
    }

    public void setEstimatedDistance(double estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }

    public List<T> pathSteps() {
        @SuppressWarnings("unchecked")
        T currentNode = (T) this;

        LinkedList<T> path = new LinkedList<>();
        do {
            path.push(currentNode);
            currentNode = currentNode.getParent();
        } while (currentNode != null);
        return path;
    }

    public void updatePathInfo(T adjacentNode) {
        this.parent = adjacentNode;
        this.distanceFromBeginning = adjacentNode.distanceFromStart() + this.movementCost();
    }

    public void clearPathInfo() {
        this.parent = null;
        distanceFromBeginning = Double.POSITIVE_INFINITY;
    }

    public boolean isCloserToStartThan(Node<T> node) {
        return this.distanceFromStart() < node.distanceFromStart();
    }

    public boolean isEstimatedCloserToStartThan(Node<T> node) {
        return this.distanceFromStart() + this.estimatedDistance < node.distanceFromStart() + node.estimatedDistance;

    }

    public abstract double movementCost();



}
