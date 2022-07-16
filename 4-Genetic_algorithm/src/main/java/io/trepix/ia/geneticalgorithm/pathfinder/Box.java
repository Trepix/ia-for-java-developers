package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.pathfinder.Labyrinth.Displacement;

import java.util.LinkedList;
import java.util.List;

public class Box {
    private final int x;
    private final int y;

    private final List<Box> adjacentBoxes;
    
    public Box(int x, int y) {
        this.x = x;
        this.y = y;
        this.adjacentBoxes = new LinkedList<>();
    }

    public void addAdjacent(Box box) {
        box.adjacentBoxes.add(this);
        this.adjacentBoxes.add(box);
    }

    public boolean isIntersection() {
        return this.adjacentBoxes.size() > 2;
    }

    public int manhattanDistanceTo(Box box) {
        return Math.abs(this.y - box.y) + Math.abs(this.x - box.x);
    }

    public Box move(Displacement displacement) {
        int y = this.y + displacement.vertical();
        int x = this.x + displacement.horizontal();
        return adjacentBoxes.stream().filter(box -> box.y == y && box.x == x).findFirst().orElse(this);
    }

}
