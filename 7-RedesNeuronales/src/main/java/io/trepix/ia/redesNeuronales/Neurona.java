package redNeuronal;

import java.util.Random;

// Neurona formal de nuestra red
public class Neurona {
    protected double[] pesos;
    protected double salida;
    
    public double getSalida() {
        return salida;
    }
    
    public double getPeso(int index) {
        return pesos[index];
    }
    
    public void setPeso(int index, double valor) {
        pesos[index] = valor;
    }
    
    public Neurona(int _numEntradas) {
        salida = Double.NaN;
        
        Random generador = new Random();
        pesos = new double[_numEntradas + 1];
        for (int i = 0; i < _numEntradas + 1; i++) {
            pesos[i] = generador.nextDouble() * 2.0 - 1.0;
        }
    }
    
    protected double Evaluar(double[] entradas) {
        if (Double.isNaN(salida)) {
            double x = 0.0;
            int numEntradas = entradas.length;
            for (int i = 0; i < numEntradas; i++) {
                x += entradas[i] * pesos[i];
            }
            x += pesos[numEntradas];
            salida = 1.0 / (1.0 + Math.exp(-1.0 * x));
        }
        
        return salida;
    }
    
    protected double Evaluar(PuntoND punto) {
        return Evaluar(punto.entradas);
    }
    
    protected void Eliminar() {
        salida = Double.NaN;
    }
}
