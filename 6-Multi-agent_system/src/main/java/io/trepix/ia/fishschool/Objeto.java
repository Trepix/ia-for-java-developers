package io.trepix.ia.fishschool;

import java.util.Objects;

// Objeto en el mundo (obstaculo o pez)
public class Objeto {
    public double posX;
    public double posY;
    
    public Objeto() {}
    public Objeto(double _x, double _y) {
        posX = _x;
        posY = _y;
    }
    
    public double DistanciaCuadrado(Objeto o) {
        return (o.posX - posX) * (o.posX - posX) + (o.posY - posY) * (o.posY - posY);
    }
        
    public double Distancia(Objeto o) {
        return Math.sqrt(DistanciaCuadrado(o));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Objeto objeto)) return false;
        return Double.compare(objeto.posX, posX) == 0 && Double.compare(objeto.posY, posY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
}
