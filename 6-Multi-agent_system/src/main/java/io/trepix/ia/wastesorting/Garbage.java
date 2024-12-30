package io.trepix.ia.wastesorting;

import io.trepix.ia.Position;

public class Garbage extends Objeto {
    protected final static double DISMINUCION = 0.6;
    
    protected int type;
    protected int size = 1;

    public int getSize() {
        return size;
    }

    public Garbage(Position position, int type) {
        this.type = type;
        posX = position.x();
        posY = position.y();
    }

    public int ZonaInfluencia() {
        return 10 + 8 * (size - 1);
    }
    
    protected void increase() {
        size++;
    }
    
    private void decrease() {
        size--;
    }
    
    protected double probabilityToTakeIt() {
        return Math.pow(DISMINUCION, size -1);
    }

    public Garbage take() {
        this.decrease();
        return new Garbage(new Position(this.posX, this.posY), this.type);
    }

}
