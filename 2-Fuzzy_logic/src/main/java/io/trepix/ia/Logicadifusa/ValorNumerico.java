package io.trepix.ia.Logicadifusa;

// Clase que permite asociar una variable linguística y su valor numerico
public class ValorNumerico {
    protected LinguisticVariable vl;
    protected double valor;
    
    // Constructor
    public ValorNumerico(LinguisticVariable _vl, double _valor) {
        vl = _vl;
        valor = _valor;
    }
}
