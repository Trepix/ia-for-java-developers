package Logicadifusa;

// Clase que permite asociar una variable linguística y su valor numerico
public class ValorNumerico {
    protected VariableLinguistica vl;
    protected double valor;
    
    // Constructor
    public ValorNumerico(VariableLinguistica _vl, double _valor) {
        vl = _vl;
        valor = _valor;
    }
}
