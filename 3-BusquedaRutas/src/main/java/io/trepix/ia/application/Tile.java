package io.trepix.ia.application;

import io.trepix.ia.busquedaCaminos.Node;
public class Tile extends Node {
    protected TileType type;
    protected int row;
    protected int column;
    
    // Constructor
    public Tile(TileType type, int row, int column) {
        this.type = type;
        this.row = row;
        this.column = column;
    }
    boolean isAccessible() {
        return type.isAccessible();
    }
    
    // Devuelve el coste de la casilla
    double Coste() {
        return type.cost();
    }
    
    @Override
    public String toString() {
        return "[" + row + ";" + column + ";" + type.toString() + "]";
    }
}
