package io.trepix.ia.wastesorting;

import io.trepix.ia.Direction;
import io.trepix.ia.Position;

import java.util.ArrayList;
import java.util.Collections;

public class ClassifierAgent extends Objeto {
    protected final static double STEP = 3;
    protected final static double PROBABILITY_TO_CHANGE_DIRECTION = 0.05;
    
    protected Garbage garbage;

    private final Field field;
    protected double speedX;
    protected double speedY;
    protected boolean occupied = false;
    
    protected void normalize() {
        double speed = Math.sqrt(speedX * speedX + speedY * speedY);
        speedX /= speed;
        speedY /= speed;
    }
    
    public ClassifierAgent(Position position, Direction direction, Field field) {
        this.field = field;
        posX = position.x();
        posY = position.y();
        speedX = direction.x();
        speedY = direction.y();
        normalize();
    }
    
    public boolean isLoaded() {
        return garbage != null;
    }
    
    public void updatePosition() {
        posX += STEP * speedX;
        posY += STEP * speedY;
        double ancho = this.field.width();
        double alto = this.field.height();
        if (posX < 0) {
            posX = 0;
        }
        else if (posX > ancho) {
            posX = ancho;
        }
        if (posY < 0) {
            posY = 0;
        }
        else if (posY > alto) {
            posY = alto;
        }
    }
    
    protected void updateDirection(ArrayList<Garbage> garbage) {
        // ¿Dónde ir?
        ArrayList<Garbage> enZona = new ArrayList<>();
        enZona.addAll(garbage);
        enZona.removeIf(d -> (Distancia(d) > d.ZonaInfluencia()));
        Collections.sort(enZona, (Garbage d1, Garbage d2) -> (Distancia(d1) < Distancia(d2) ? -1: 1));
        Garbage objetivo = null;
        if (this.garbage != null) {
            enZona.removeIf(d -> d.type != this.garbage.type);
        }
        if (!enZona.isEmpty()) {
            objetivo = enZona.get(0);
        }
        
        // ¿Alcanzado un objetivo?
        if (objetivo == null || occupied) {
            // Movimiento aleatorio
            if (this.field.generator.nextDouble() < PROBABILITY_TO_CHANGE_DIRECTION) {
                speedX = this.field.generator.nextDouble() - 0.5;
                speedY = this.field.generator.nextDouble() - 0.5;
            }
            if (occupied && objetivo == null) {
                occupied = false;
            }
        }
        else {
            // Ir a objetivo
            speedX = objetivo.posX - posX;
            speedY = objetivo.posY - posY;
            // ¿Alcanzado objetivo?
            if (Distancia(objetivo) < STEP) {
                if (this.garbage == null) {
                    if (this.field.generator.nextDouble() < objetivo.probabilityToTakeIt()) {
                        this.garbage = this.field.TomarResiduo(objetivo);
                    }
                }
                else {
                    this.field.DepositarResiduo(objetivo);
                    this.garbage = null;
                }
                occupied = true;
            }
        }
        normalize();
    }
}
