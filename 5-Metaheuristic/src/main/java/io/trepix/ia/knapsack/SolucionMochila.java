package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Solution;
import java.util.ArrayList;
import java.util.StringJoiner;

// Una solución potencial = una carga posible de la mochila
public class SolucionMochila implements Solution {
    public ArrayList<Caja> contenido;
    
    public SolucionMochila() {
        contenido = new ArrayList();
    }
    
    public SolucionMochila(SolucionMochila original) {
        contenido = new ArrayList();
        contenido.addAll(original.contenido);
    }
    
    public double getPeso() {
        double pesos = 0.0;
        for (Caja b : contenido) {
            pesos += b.pesos;
        }
        return pesos;
    }
    
    @Override
    public double value() {
        double valor = 0.0;
        for (Caja b : contenido) {
            valor += b.valor;
        }
        return valor;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" - ");
        sj.add("Valor : " + value());
        sj.add("Peso : " + getPeso());
        for(Caja b : contenido) {
            sj.add(b.toString());
        }
        return sj.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SolucionMochila)) {
            return false;
        }
        SolucionMochila sol = (SolucionMochila) o;
        if (sol.contenido.size() != this.contenido.size() || sol.getPeso() != this.getPeso() || sol.value() != this.value()) {
            return false;
        }
        for(Caja b : contenido) {
            if (!sol.contenido.contains(b)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 42;
    }
}
