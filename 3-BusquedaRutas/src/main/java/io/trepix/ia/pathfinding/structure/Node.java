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

    public double getDistanceFromBeginning() {
        return distanceFromBeginning;
    }

    public void setDistanceFromBeginning(double distanceFromBeginning) {
        this.distanceFromBeginning = distanceFromBeginning;
    }

    public double getEstimatedDistance() {
        return estimatedDistance;
    }

    public void setEstimatedDistance(double estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }
}
