package application;

// Una caja en el problema de la mochila
public class Caja {
    public double pesos;
    public double valor;
    protected String nombre;
    
    public Caja(String _nombre , double _pesos, double _valor) {
        nombre = _nombre ;
        pesos = _pesos;
        valor = _valor;
    }
    
    @Override
    public String toString() {
        return nombre + " (" + pesos + ", " + valor + ")";
    }
}
