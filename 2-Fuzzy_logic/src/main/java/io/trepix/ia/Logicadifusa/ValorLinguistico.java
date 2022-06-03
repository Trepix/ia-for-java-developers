package io.trepix.ia.Logicadifusa;

// Clase que representa un valor linguistico un nombre y un conjunto difuso
public class ValorLinguistico {
    protected ConjuntoDifuso conjuntoDifuso;
    protected String nombre;

    // Constructor
    public ValorLinguistico(String _nombre , ConjuntoDifuso _ef) {
        conjuntoDifuso = _ef;
        nombre = _nombre ;
    }
    
    double ValorDePertenencia(double valor) {
        return conjuntoDifuso.ValorDePertenencia(valor);
    }
}
