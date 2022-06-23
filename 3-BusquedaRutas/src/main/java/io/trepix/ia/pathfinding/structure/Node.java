package io.trepix.ia.pathfinding.structure;

public abstract class Node<T extends Node<T>> {
    private T parent = null;
    private double distanceFromBeginning = Double.POSITIVE_INFINITY;
    private double estimatedDistance;

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public double distanceFromStart() {
        return distanceFromBeginning;
    }

    public void updateDistanceFromStart(double distanceFromBeginning) {
        this.distanceFromBeginning = distanceFromBeginning;
    }

    public void updateDistanceFromStart(Node<T> adjacentNode) {
        this.distanceFromBeginning = adjacentNode.distanceFromStart() + this.movementCost();
    }

    public double getEstimatedDistance() {
        return estimatedDistance;
    }

    public void setEstimatedDistance(double estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }

    public abstract double movementCost();

    public void updatePathInfo(T currentNode) {
        this.setParent(currentNode);
        this.updateDistanceFromStart(currentNode);
    }
}
