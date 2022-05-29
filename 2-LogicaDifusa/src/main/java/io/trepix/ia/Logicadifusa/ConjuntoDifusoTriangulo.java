package Logicadifusa;

// Conjunto difuso particular : función triángulo
// Forma :
// *         
// *        /\ 
// *       /  \
// *______/    \_________
// ***********************
public class ConjuntoDifusoTriangulo extends ConjuntoDifuso {
    // Constructor
    public ConjuntoDifusoTriangulo(double min, double max, double inicioBase, double sumat, double finBase) {
        super(min, max);
        Agregar(new Punto2D(min, 0));
        Agregar(new Punto2D(inicioBase, 0));
        Agregar(new Punto2D(sumat, 1));
        Agregar(new Punto2D(finBase, 0));
        Agregar(new Punto2D(max, 0));
    }
}
