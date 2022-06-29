package algoritmogenetico;

import java.util.ArrayList;
import java.util.StringJoiner;

// Un individuo en una población
public abstract class Individuo {
    // Atributos
    protected double fitness;
    protected ArrayList<IGen> genoma;
    
    // Métodos
    public double getFitness() {
        return fitness;
    }
    
    public abstract void Mutar();
    public abstract double Evaluar();
    
    @Override
    public String toString() {
        String gen = "(" + fitness + ")";
        StringJoiner sj = new StringJoiner(" - ");
        sj.add(gen);
        for(IGen g : genoma) {
            sj.add(g.toString());
        }
        return sj.toString();
    }
}
