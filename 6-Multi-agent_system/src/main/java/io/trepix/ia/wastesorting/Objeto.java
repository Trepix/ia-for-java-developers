package io.trepix.ia.wastesorting;

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
        double epsilon = 1E-10;
        return equals(objeto.posX, posX, epsilon) && equals(objeto.posY, posY, epsilon);
    }

    private boolean equals(double d1, double d2, double epsilon) {
        return Math.abs(d1 - d2) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
}
