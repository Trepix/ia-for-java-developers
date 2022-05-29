package bancoPeces;

// Una zona a evitar para los peces. Desaparece automaticamente pasado un instante
public class ZonaAEvitar extends Objet {
    protected double radio;
    protected int tiempoRestante = 500;
    
    public ZonaAEvitar(double _x, double _y, double _radio) {
        posX = _x;
        posY = _y;
        radio = _radio;
    }
    
    public double getRadio() {
        return radio;
    }
    
    public void Actualizar() {
        tiempoRestante--;
    }
    
    public boolean estaMuerto() {
        return tiempoRestante <= 0;
    }
}
