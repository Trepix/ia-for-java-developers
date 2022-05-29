package bancoPeces;

// Objeto en el mundo (obstaculo o pez)
public class Objet {
    public double posX;
    public double posY;
    
    public Objet() {}
    public Objet(double _x, double _y) {
        posX = _x;
        posY = _y;
    }
    
    public double DistanciaCuadrado(Objet o) {
        return (o.posX - posX) * (o.posX - posX) + (o.posY - posY) * (o.posY - posY);
    }
        
    public double Distancia(Objet o) {
        return Math.sqrt(DistanciaCuadrado(o));
    }
}
