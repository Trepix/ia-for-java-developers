package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;

import java.util.List;
import java.util.Objects;

// Un pez, gestionado por un agente
public class Fish extends Objeto {
    // Constantes
    public static final double PASO = 3;
    public static final double DISTANCIA_MIN = 5;
    public static final double DISTANCIA_MIN_CUADRADO = 25;
    public static final double DISTANCIA_MAX = 40;
    public static final double DISTANCIA_MAX_CUADRADO = 1600;
    
    // Atributos
    protected double velocidadX;
    protected double velocidadY;

    public Fish(Position position, Direction direction) {
        posX = position.x();
        posY = position.y();
        velocidadX = direction.x();
        velocidadY = direction.y();
    }

    protected void ActualizarPosicion() {
        posX += PASO * velocidadX;
        posY += PASO * velocidadY;
    }
    
    protected boolean Alineado(Fish p) {
        double distanciaCuadrado = DistanciaCuadrado(p);
        return (distanciaCuadrado < DISTANCIA_MAX_CUADRADO && distanciaCuadrado > DISTANCIA_MIN_CUADRADO);
    }
    
    protected double DistanciaAlMuro(Bounds bounds) {
        double min = Math.min(posX - bounds.lowerWidth(), posY - bounds.lowerHeight());
        min = Math.min(min, bounds.upperWidth() - posX);
        min = Math.min(min, bounds.upperHeight() - posY);
        return min;
    }
    
    protected void Normalizar() {
        double ancho = Math.sqrt(velocidadX * velocidadX + velocidadY * velocidadY);
        velocidadX /= ancho;
        velocidadY /= ancho;
    }
    
    protected boolean EvitarMuros(Bounds bounds) {
        PararEnMuro(bounds);
        double distancia = DistanciaAlMuro(bounds);
        if (distancia < DISTANCIA_MIN) {
            CambiarDireccionMuro(distancia, bounds);
            Normalizar();
            return true;
        }
        return false;
    }
    
    private void PararEnMuro(Bounds bounds) {
        if (posX < bounds.lowerWidth()) {
            posX = bounds.lowerWidth();
        }
        else if (posY < bounds.lowerHeight()) {
            posY = bounds.lowerHeight();
        }
        else if (posX > bounds.upperWidth()) {
            posX = bounds.upperWidth();
        }
        else if (posY > bounds.upperHeight()) {
            posY = bounds.upperHeight();
        }        
    }
    
    private void CambiarDireccionMuro(double distancia, Bounds bounds) {
        if (distancia == (posX - bounds.lowerWidth())) {
            velocidadX += 0.3;
        }
        else if (distancia == (posY - bounds.lowerHeight())) {
            velocidadY += 0.3; 
        } 
        else if (distancia == (bounds.upperWidth() - posX)) {
            velocidadX -= 0.3;
        } 
        else if (distancia == (bounds.upperHeight() - posY)) {
            velocidadY -= 0.3;
        }         
    }
    
    protected boolean EvitarObstaculos(List<Obstacle> obstaculos) {
        if (!obstaculos.isEmpty()) {
            // Búsqueda del obstaculo más cercano
            Obstacle obstaculoCercano = obstaculos.get(0);
            double distanciaCuadrado = DistanciaCuadrado(obstaculoCercano);
            for (Obstacle o : obstaculos) {
                if (DistanciaCuadrado(o) < distanciaCuadrado) {
                    obstaculoCercano = o;
                    distanciaCuadrado = DistanciaCuadrado(o);
                }
            }
            
            if (distanciaCuadrado < 4*(obstaculoCercano.radius() * obstaculoCercano.radius())) {
                // Si collision, cálculo del vecdor diff
                double distancia = Math.sqrt(distanciaCuadrado);
                double difX = (obstaculoCercano.x() - posX) / distancia;
                double difY = (obstaculoCercano.y() - posY) / distancia;
                velocidadX = velocidadX - difX / 2;
                velocidadY = velocidadY - difY / 2;
                Normalizar();
                return true;
            }
        }
        return false;        
    }
    
    protected boolean EvitarPeces(Fish[] peces) {
        // Búsqueda del pez más cercano
        Fish p;
        if (!peces[0].equals(this)) {
            p = peces[0];
        }
        else {
            p = peces[1];
        }
        double distanciaCuadrado = DistanciaCuadrado(p);
        for (Fish fish : peces) {
            if (DistanciaCuadrado(fish) < distanciaCuadrado && !fish.equals(this)) {
                p = fish;
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
    
    protected void CalcularDireccionMedia(Fish[] peces) {
        double velocidadXTotal = 0;
        double velocidadYTotal = 0;
        int numTotal = 0;
        for (Fish p : peces) {
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
    
    protected void evolve(Fish[] peces, List<Obstacle> obstaculos, Bounds bounds) {
        if (!EvitarMuros(bounds)) {
            if (!EvitarObstaculos(obstaculos)) {
                if (!EvitarPeces(peces)) {
                    CalcularDireccionMedia(peces);
                }
            }
        }
        ActualizarPosicion();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fish fish)) return false;
        double epsilon = 1E-15;
        return equals(fish.velocidadX, velocidadX, epsilon) && equals(fish.velocidadY, velocidadY, epsilon) &&
                super.equals(o);
    }

    private boolean equals(double d1, double d2, double epsilon) {
        return Math.abs(d1 - d2) < epsilon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocidadX, velocidadY);
    }
}
