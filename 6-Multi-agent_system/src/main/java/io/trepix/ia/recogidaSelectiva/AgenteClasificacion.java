package io.trepix.ia.recogidaSelectiva;

import java.util.ArrayList;
import java.util.Collections;

// Agente que va recoger los residuos
public class AgenteClasificacion extends Objeto {
    protected final static double PASO = 3;
    protected final static double PROB_CHGT_DIRECTION = 0.05;
    
    protected Residuo carga;

    private final Field field;
    protected double velocidadX;
    protected double velocidadY;
    protected boolean ocupado = false;
    
    protected void Normalizar() {
        double ancho = Math.sqrt(velocidadX * velocidadX + velocidadY * velocidadY);
        velocidadX /= ancho;
        velocidadY /= ancho;
    }
    
    public AgenteClasificacion(double _posX, double _posY, Field field) {
        this.field = field;
        posX = _posX;
        posY = _posY;
        velocidadX = this.field.generador.nextDouble() - 0.5;
        velocidadY = this.field.generador.nextDouble() - 0.5;
        Normalizar();
    }
    
    public boolean estaCargado() {
        return carga != null;
    }
    
    public void ActualizarPosicion() {
        posX += PASO * velocidadX;
        posY += PASO * velocidadY;
        double ancho = this.field.getAncho();
        double alto = this.field.getAlto();
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
    
    protected void ActualizarDireccion(ArrayList<Residuo> residuos) {
        // ¿Dónde ir?
        ArrayList<Residuo> enZona = new ArrayList<>();
        enZona.addAll(residuos);
        enZona.removeIf(d -> (Distancia(d) > d.ZonaInfluencia()));
        Collections.sort(enZona, (Residuo d1, Residuo d2) -> (Distancia(d1) < Distancia(d2) ? -1: 1));
        Residuo objetivo = null;
        if (carga != null) {
            enZona.removeIf(d -> d.type != carga.type);
        }
        if (!enZona.isEmpty()) {
            objetivo = enZona.get(0);
        }
        
        // ¿Alcanzado un objetivo?
        if (objetivo == null || ocupado) {
            // Movimiento aleatorio
            if (this.field.generador.nextDouble() < PROB_CHGT_DIRECTION) {
                velocidadX = this.field.generador.nextDouble() - 0.5;
                velocidadY = this.field.generador.nextDouble() - 0.5;
            }
            if (ocupado && objetivo == null) {
                ocupado = false;
            }
        }
        else {
            // Ir a objetivo
            velocidadX = objetivo.posX - posX;
            velocidadY = objetivo.posY - posY;
            // ¿Alcanzado objetivo?
            if (Distancia(objetivo) < PASO) {
                if (carga == null) {
                    if (this.field.generador.nextDouble() < objetivo.ProbaDeTomar()) {
                        carga = this.field.TomarResiduo(objetivo);
                    }
                }
                else {
                    this.field.DepositarResiduo(objetivo);
                    carga = null;
                }
                ocupado = true;
            }
        }
        Normalizar();
    }
}
