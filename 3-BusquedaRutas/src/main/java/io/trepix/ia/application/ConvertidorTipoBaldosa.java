package io.trepix.ia.application;

// Clase herramienta que gestiona los typos de terreno
class ConvertidorTipoBaldosa {
    public static TipoBaldosa CharToType(char c) {
        switch (c) {
            case ' ' :
                return TipoBaldosa.Hierba;
            case '*' :
                return TipoBaldosa.Arbol;
            case '=' :
                return TipoBaldosa.Puente;
            case 'X' :
                return TipoBaldosa.Agua;
            case '.' :
                return TipoBaldosa.Camino;
        }
        return null;
    }
}
