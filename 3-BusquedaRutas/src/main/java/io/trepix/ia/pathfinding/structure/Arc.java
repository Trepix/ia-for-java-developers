package io.trepix.ia.pathfinding.structure;

public record Arc<T extends Node<T>>(T origin, T destination, double cost) {
    public void updatePathInfo() {
        this.destination().updatePathInfo(this.origin());
    }

    public boolean isShorterThanKnownPathToDestination() {
        return this.origin().distanceFromStart() + this.cost() < this.destination().distanceFromStart();
    }
}
