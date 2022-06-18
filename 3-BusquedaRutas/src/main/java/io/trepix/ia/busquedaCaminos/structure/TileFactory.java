package io.trepix.ia.busquedaCaminos.structure;

import io.trepix.ia.busquedaCaminos.structure.Tile;
import io.trepix.ia.busquedaCaminos.structure.TileType;

import static io.trepix.ia.busquedaCaminos.structure.TileType.*;

public class TileFactory {

    public static Tile create(char tileChar, int row, int column) {
        return switch (tileChar) {
            case ' ' -> new Tile(Hierba, row, column);
            case '*' -> new Tile(Arbol, row, column);
            case '=' -> new Tile(Puente, row, column);
            case 'X' -> new Tile(Agua, row, column);
            case '.' -> new Tile(Camino, row, column);
            default -> null;
        };
    }

}
