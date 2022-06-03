package io.trepix.ia.Logicadifusa;

// Conjunto difuso particular : 1/2 trapecio derecha
// Forme :
// *         __________
// *        / 
// *       /  
// *______/
// ***********************
public class ConjuntoDifusoTrapecioDerecha extends ConjuntoDifuso {
    // Constructor
    public ConjuntoDifusoTrapecioDerecha(double min, double max, double finLadoAbajo, double inicioLadoArriba) {
        super(min, max);
        Agregar(new Punto2D(min, 0));
        Agregar(new Punto2D(finLadoAbajo, 0));
        Agregar(new Punto2D(inicioLadoArriba, 1));
        Agregar(new Punto2D(max, 1));
    }
}
