package redNeuronal;

// Punto usado en la red de neuronas, con varias entradas y salidas
public class PuntoND {
    public final double[] entradas;
    public final double[] salidas;
    
    public PuntoND(String str, int _numSalidas) {
        String[] contenido = str.split("\t");
        entradas = new double[contenido.length - _numSalidas];
        for (int i = 0; i < entradas.length; i++) {
            entradas[i] = Double.parseDouble(contenido[i]);
        }
        salidas = new double[_numSalidas];
        for (int i = 0; i < _numSalidas; i++) {
            salidas[i] = Double.parseDouble(contenido[entradas.length + i]);
        }
    }
}
