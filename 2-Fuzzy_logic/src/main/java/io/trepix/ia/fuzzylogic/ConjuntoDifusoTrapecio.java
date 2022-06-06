package io.trepix.ia.fuzzylogic;

// Conjunto difuso particular : función trapecio
// Forme :
// *         ____
// *        /    \ 
// *       /      \
// *______/        \______
// ***********************
public class ConjuntoDifusoTrapecio extends FuzzySet {
    // Constructor
    public ConjuntoDifusoTrapecio(double min, double max, double inicioBase, double inicioLado, double finLado, double finBase) {
        super(min, max);
        Agregar(new Punto2D(min, 0));
        Agregar(new Punto2D(inicioBase, 0));
        Agregar(new Punto2D(inicioLado, 1));
        Agregar(new Punto2D(finLado, 1));
        Agregar(new Punto2D(finBase, 0));
        Agregar(new Punto2D(max, 0));
    }
}
