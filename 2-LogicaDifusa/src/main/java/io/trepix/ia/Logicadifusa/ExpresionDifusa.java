package Logicadifusa;

// Expresión difusa : Variable IS Valor
public class ExpresionDifusa {
    protected VariableLinguistica varL;
    protected String nombreValorLinguistico;
    
    // Constructor
    public ExpresionDifusa(VariableLinguistica _vl, String _valor) {
        varL = _vl;
        nombreValorLinguistico = _valor;
    }
}
