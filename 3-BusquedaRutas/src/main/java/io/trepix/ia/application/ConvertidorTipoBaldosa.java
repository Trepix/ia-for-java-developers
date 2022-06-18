package io.trepix.ia.application;

// Clase herramienta que gestiona los typos de terreno
class ConvertidorTipoBaldosa {
    public static TileType CharToType(char c) {
        switch (c) {
            case ' ' :
                return TileType.Hierba;
            case '*' :
                return TileType.Arbol;
            case '=' :
                return TileType.Puente;
            case 'X' :
                return TileType.Agua;
            case '.' :
                return TileType.Camino;
        }
        return null;
    }
}
