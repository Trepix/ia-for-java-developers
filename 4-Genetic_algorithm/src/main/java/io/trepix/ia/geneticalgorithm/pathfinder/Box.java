package io.trepix.ia.geneticalgorithm.pathfinder;

public class Box {
    private int i;
    private int j;
    
    public Box(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int manhattanDistanceTo(Box box) {
        return Math.abs(this.i - box.i) + Math.abs(this.j - box.j);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return i == box.i && j == box.j;
    }


}
