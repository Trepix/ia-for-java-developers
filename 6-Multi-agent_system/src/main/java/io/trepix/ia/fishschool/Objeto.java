package io.trepix.ia.fishschool;

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
}
