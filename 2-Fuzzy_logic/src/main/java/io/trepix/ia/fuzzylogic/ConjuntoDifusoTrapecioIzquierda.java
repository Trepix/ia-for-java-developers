package io.trepix.ia.fuzzylogic;

// Conjunto difuso particular : 1/2 trapecio izquierda
// Forme :
// *_____
// *     \
// *      \
// *       \_________
// ***********************
public class ConjuntoDifusoTrapecioIzquierda extends ConjuntoDifuso {
    // Constructor
    public ConjuntoDifusoTrapecioIzquierda(double min, double max, double finLadoArriba, double inicioLadoAbajo) {
        super(min, max);
        Agregar(new Punto2D(min, 1));
        Agregar(new Punto2D(finLadoArriba, 1));
        Agregar(new Punto2D(inicioLadoAbajo, 0));
        Agregar(new Punto2D(max, 0));
    }
}
