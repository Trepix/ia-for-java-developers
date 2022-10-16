package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;
import io.trepix.ia.Bounds.Bound;

import java.util.List;
import java.util.Objects;

public class Fish extends Objeto {
    public static final double PASO = 3;
    public static final double MIN_DISTANCE_TO_AVOID_COLLISION_WITH_BOUND = 5;
    public static final double DISTANCIA_MIN_CUADRADO = 25;
    public static final double DISTANCIA_MAX = 40;
    public static final double DISTANCIA_MAX_CUADRADO = 1600;

    protected double velocidadX;
    protected double velocidadY;

    private Position position;

    private Direction direction;

    public Fish(Position position, Direction direction) {
        updatePosition(position);
        updateDirection(direction);
    }

    protected void ActualizarPosicion() {
        posX += PASO * velocidadX;
        posY += PASO * velocidadY;
    }
    
    protected boolean Alineado(Fish p) {
        double distanciaCuadrado = DistanciaCuadrado(p);
        return (distanciaCuadrado < DISTANCIA_MAX_CUADRADO && distanciaCuadrado > DISTANCIA_MIN_CUADRADO);
    }
    
    protected void Normalizar() {
        double ancho = Math.sqrt(velocidadX * velocidadX + velocidadY * velocidadY);
        velocidadX /= ancho;
        velocidadY /= ancho;
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
    
    protected void evolve(Fish[] fishes, List<Obstacle> obstacles, Bounds bounds) {
        shiftInside(bounds);
        if (hasToAvoid(bounds)) {
            moveAwayFrom(bounds);
        }
        else if (!EvitarObstaculos(obstacles)) {
            if (!EvitarPeces(fishes)) {
                CalcularDireccionMedia(fishes);
            }
        }
        ActualizarPosicion();
    }

    private void shiftInside(Bounds bounds) {
        var position = getPosition().shiftInside(bounds);
        updatePosition(position);
    }

    private boolean hasToAvoid(Bounds bounds) {
        Bound bound = bounds.nearestTo(getPosition());
        double distance = bound.distanceTo(position);
        return distance < MIN_DISTANCE_TO_AVOID_COLLISION_WITH_BOUND;
    }

    private void moveAwayFrom(Bounds bounds) {
        Bound nearestBound = bounds.nearestTo(getPosition());
        updateDirection(switch (nearestBound.name()) {
            case LOWER_WIDTH -> getDirection().turnToRight();
            case UPPER_WIDTH -> getDirection().turnToLeft();
            case LOWER_HEIGHT -> getDirection().turnUpwards();
            case UPPER_HEIGHT -> getDirection().turnDownwards();
        });
    }

    //Narrowed change methods

    private Position getPosition() {
        return new Position(posX, posY);
    }

    private void updatePosition(Position position) {
        posX = position.x();
        posY = position.y();
        this.position = position;
    }

    private Direction getDirection() {
        return new Direction(velocidadX, velocidadY);
    }

    private void updateDirection(Direction direction) {
        velocidadX = direction.x();
        velocidadY = direction.y();
        this.direction = direction;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fish fish)) return false;
        double epsilon = 1E-10;
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
