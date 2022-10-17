package io.trepix.ia.fishschool;

import io.trepix.ia.Bounds;
import io.trepix.ia.Bounds.Bound;

import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparing;

public class Fish extends Objeto {
    public static final double PASO = 3;
    public static final double MIN_DISTANCE_TO_AVOID_COLLISION_WITH_BOUND = 5;
    public static final double DISTANCIA_MIN_CUADRADO = 25;
    public static final double DISTANCIA_MAX_CUADRADO = 1600;

    protected double velocidadX;
    protected double velocidadY;

    private Position position;

    private UnitaryDirection direction;

    public Fish(Position position, UnitaryDirection direction) {
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

    protected boolean dodge(List<Obstacle> obstacles) {
        if (obstacles.isEmpty()) {
            return false;
        }

        Obstacle nearestObstacle = obstacles.stream().min(comparing(this::distanceTo)).get();
        if (this.willCollideWith(nearestObstacle)) {
            UnitaryDirection directionToObstacle = nearestObstacle.directionFrom(getPosition());
            updateDirection(getDirection().turnAwayFrom(directionToObstacle));
            return true;
        }
        return false;
    }

    private boolean willCollideWith(Obstacle nearestObstacle) {
        return this.distanceTo(nearestObstacle) < 2 * nearestObstacle.radius();
    }

    private double distanceTo(Obstacle obstacle) {
        return obstacle.distanceFrom(getPosition());
    }

    protected boolean EvitarPeces(Fish[] peces) {
        // Búsqueda del pez más cercano
        Fish p;
        if (!peces[0].equals(this)) {
            p = peces[0];
        } else {
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
        if (!moveAwayFrom(bounds) && !dodge(obstacles)) {
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

    private boolean moveAwayFrom(Bounds bounds) {
        Bound nearestBound = bounds.nearestTo(getPosition());
        double distance = nearestBound.distanceTo(position);
        if (distance < MIN_DISTANCE_TO_AVOID_COLLISION_WITH_BOUND) {
            updateDirection(switch (nearestBound.name()) {
                case LOWER_WIDTH -> getDirection().turnToRight();
                case UPPER_WIDTH -> getDirection().turnToLeft();
                case LOWER_HEIGHT -> getDirection().turnUpwards();
                case UPPER_HEIGHT -> getDirection().turnDownwards();
            });
            return true;
        }
        return false;
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

    private UnitaryDirection getDirection() {
        return new UnitaryDirection(velocidadX, velocidadY);
    }

    private void updateDirection(UnitaryDirection direction) {
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
