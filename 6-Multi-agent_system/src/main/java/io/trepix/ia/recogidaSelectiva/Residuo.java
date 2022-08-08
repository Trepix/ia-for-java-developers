package io.trepix.ia.recogidaSelectiva;

import io.trepix.ia.bancoPeces.Objeto;

// Residuos en el entorno
public class Residuo extends Objeto {
    protected final static double DISMINUCION = 0.6;
    
    protected int type;
    protected int tamaño = 1;
    
    public int getType() {
        return type;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public Residuo(double _posX, double _posY, int _tipo) {
        type = _tipo;
        posX = _posX;
        posY = _posY;
    }
    
    public Residuo(Residuo d) {
        posX = d.posX;
        posY = d.posY;
        type = d.type;
    }
    
    public int ZonaInfluencia() {
        return 10 + 8 * (tamaño - 1);
    }
    
    protected void AumentarTamaño() {
        tamaño++;
    }
    
    protected void ReducirTamaño() {
        tamaño--;
    }
    
    protected double ProbaDeTomar() {
        return Math.pow(DISMINUCION, tamaño-1);
    }
}
