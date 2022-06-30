package io.trepix.ia.algoritmogenetico.laberinto;

import io.trepix.ia.algoritmogenetico.IGen;
import io.trepix.ia.algoritmogenetico.Parametros;

// Los genes para el laberinto
public class LabGen implements IGen {
    public Laberinto.Direccion dirección;
    
    public LabGen() {
        dirección = Laberinto.Direccion.values()[Parametros.random.nextInt(4)];
    }
    
    public LabGen(LabGen g) {
        dirección = g.dirección;
    }
    
    @Override
    public String toString() {
        return dirección.name().substring(0, 1);
    }
    
    @Override
    public void Mutar() {
        dirección = Laberinto.Direccion.values()[Parametros.random.nextInt(4)];
    }

}
