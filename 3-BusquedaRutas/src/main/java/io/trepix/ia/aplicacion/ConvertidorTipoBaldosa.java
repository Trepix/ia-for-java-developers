package application;

// Clase herramienta que gestiona los typos de terreno
class ConversorTipoBaldosa {
    public static TipoBaldosa CharToType(char c) {
        switch (c) {
            casilla ' ' :
                return TipoBaldosa.Hierba;
            casilla '*' :
                return TipoBaldosa.Arbol;
            casilla '=' :
                return TipoBaldosa.Puente;
            casilla 'X' : 
                return TipoBaldosa.Agua;
            casilla '.' :
                return TipoBaldosa.Camino;
        }
        return null;
    }
}
