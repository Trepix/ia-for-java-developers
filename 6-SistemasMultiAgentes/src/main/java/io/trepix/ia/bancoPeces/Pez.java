package bancoPeces;

import java.util.ArrayList;

// Un pez, gestionado por un agente
public class Pez extends Objet {
    // Constantes
    public static final double PASO = 3;
    public static final double DISTANCIA_MIN = 5;
    public static final double DISTANCIA_MIN_CUADRADO = 25;
    public static final double DISTANCIA_MAX = 40;
    public static final double DISTANCIA_MAX_CUADRADO = 1600;
    
    // Atributos
    protected double velocidadX;
    protected double velocidadY;
    
    // Métodos
    public Pez(double _x, double _y, double _dir) {
        posX = _x;
        posY = _y;
        velocidadX = Math.cos(_dir);
        velocidadY = Math.sin(_dir);
    }
    
    public double getVelocidadX() {
        return velocidadX;
    }
    
    public double getVelocidadY() {
        return velocidadY;
    }
    
    protected void ActualizarPosicion() {
        posX += PASO * velocidadX;
        posY += PASO * velocidadY;
    }
    
    protected boolean Alineado(Pez p) {
        double distanciaCuadrado = DistanciaCuadrado(p);
        return (distanciaCuadrado < DISTANCIA_MAX_CUADRADO && distanciaCuadrado > DISTANCIA_MIN_CUADRADO);
    }
    
    protected double DistanciaAlMuro(double murXMin, double murYMin, double murXMax, double murYMax) {
        double min = Math.min(posX - murXMin, posY - murYMin);
        min = Math.min(min, murXMax - posX);
        min = Math.min(min, murYMax - posY);
        return min;
    }
    
    protected void Normalizar() {
        double ancho = Math.sqrt(velocidadX * velocidadX + velocidadY * velocidadY);
        velocidadX /= ancho;
        velocidadY /= ancho;
    }
    
    protected boolean EvitarMuros(double murXMin, double murYMin, double murXMax, double murYMax) {
        PararEnMuro(murXMin, murYMin, murXMax, murYMax);
        double distancia = DistanciaAlMuro(murXMin, murYMin, murXMax, murYMax);
        if (distancia < DISTANCIA_MIN) {
            CambiarDireccionMuro(distancia, murXMin, murYMin, murXMax, murYMax);
            Normalizar();
            return true;
        }
        return false;
    }
    
    private void PararEnMuro(double murXMin, double murYMin, double murXMax, double murYMax) {
        if (posX < murXMin) {
            posX = murXMin;
        }
        else if (posY < murYMin) {
            posY = murYMin;
        }
        else if (posX > murXMax) {
            posX = murXMax;
        }
        else if (posY > murYMax) {
            posY = murYMax;
        }        
    }
    
    private void CambiarDireccionMuro(double distancia, double murXMin, double murYMin, double murXMax, double murYMax) {
        if (distancia == (posX - murXMin)) {
            velocidadX += 0.3;
        }
        else if (distancia == (posY - murYMin)) { 
            velocidadY += 0.3; 
        } 
        else if (distancia == (murXMax - posX)) {
            velocidadX -= 0.3;
        } 
        else if (distancia == (murYMax - posY)) {
            velocidadY -= 0.3;
        }         
    }
    
    protected boolean EvitarObstaculos(ArrayList<ZonaAEvitar> obstaculos) {
        if (!obstaculos.isEmpty()) {
            // Búsqueda del obstaculo más cercano
            ZonaAEvitar obstaculoCercano = obstaculos.get(0);
            double distanciaCuadrado = DistanciaCuadrado(obstaculoCercano);
            for (ZonaAEvitar o : obstaculos) {
                if (DistanciaCuadrado(o) < distanciaCuadrado) {
                    obstaculoCercano = o;
                    distanciaCuadrado = DistanciaCuadrado(o);
                }
            }
            
            if (distanciaCuadrado < 4*(obstaculoCercano.radio * obstaculoCercano.radio)) {
                // Si collision, cálculo del vecdor diff
                double distancia = Math.sqrt(distanciaCuadrado);
                double difX = (obstaculoCercano.posX - posX) / distancia;
                double difY = (obstaculoCercano.posY - posY) / distancia;
                velocidadX = velocidadX - difX / 2;
                velocidadY = velocidadY - difY / 2;
                Normalizar();
                return true;
            }
        }
        return false;        
    }
    
    protected boolean EvitarPeces(Pez[] peces) {
        // Búsqueda del pez más cercano
        Pez p;
        if (!peces[0].equals(this)) {
            p = peces[0];
        }
        else {
            p = peces[1];
        }
        double distanciaCuadrado = DistanciaCuadrado(p);
        for (Pez pez : peces) {
            if (DistanciaCuadrado(pez) < distanciaCuadrado && !pez.equals(this)) {
                p = pez;
                distanciaCuadrado = DistanciaCuadrado(p);
            }
        }
        
        // Evitación
        if (distanciaCuadrado < DISTANCIA_MIN_CUADRADO) {
            double distancia = Math.sqrt(distanciaCuadrado);
            double difX = (p.posX - posX) / distancia;
            double difY = (p.posY - posY) / distancia;
            velocidadX = velocidadX - difX / 4;
            velocidadY = velocidadY - difY / 4;
            Normalizar();
            return true;
        }
        return false;
    }
    
    protected void CalcularDireccionMedia(Pez[] peces) {
        double velocidadXTotal = 0;
        double velocidadYTotal = 0;
        int numTotal = 0;
        for (Pez p : peces) {
            if (Alineado(p)) {
                velocidadXTotal += p.velocidadX;
                velocidadYTotal += p.velocidadY;
                numTotal++;
            }
        }
        if (numTotal >= 1) {
            velocidadX = (velocidadXTotal / numTotal + velocidadX) / 2;
            velocidadY = (velocidadYTotal / numTotal + velocidadY) / 2;
            Normalizar();
        }
    }
    
    protected void Actualizar(Pez[] peces, ArrayList<ZonaAEvitar> obstaculos, double ancho, double alto) {
        if (!EvitarMuros(0,0,ancho,alto)) {
            if (!EvitarObstaculos(obstaculos)) {
                if (!EvitarPeces(peces)) {
                    CalcularDireccionMedia(peces);
                }
            }
        }
        ActualizarPosicion();
    }
}
