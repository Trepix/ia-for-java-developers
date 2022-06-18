package io.trepix.ia.busquedaCaminos;
public abstract class Node {
    public Node parent = null;
    public double distanceFromBeginning = Double.POSITIVE_INFINITY;
    public double estimatedDistance;
}
