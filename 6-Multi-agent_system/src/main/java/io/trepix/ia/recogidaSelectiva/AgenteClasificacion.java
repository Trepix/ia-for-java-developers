package io.trepix.ia.recogidaSelectiva;

import io.trepix.ia.bancoPeces.Objeto;
import io.trepix.ia.bancoPeces.Objeto;

import java.util.ArrayList;
import java.util.Collections;

// Agente que va recoger los residuos
public class AgenteClasificacion extends Objeto {
    protected final static double PASO = 3;
    protected final static double PROB_CHGT_DIRECTION = 0.05;
    
    protected Residuo carga;
    protected double velocidadX;
    protected double velocidadY;
    protected boolean ocupado = false;
    
    protected void Normalizar() {
        double ancho = Math.sqrt(velocidadX * velocidadX + velocidadY * velocidadY);
        velocidadX /= ancho;
        velocidadY /= ancho;
    }
    
    public AgenteClasificacion(double _posX, double _posY) {
        posX = _posX;
        posY = _posY;
        velocidadX = Entorno.getInstance().generador.nextDouble() - 0.5;
        velocidadY = Entorno.getInstance().generador.nextDouble() - 0.5;
        Normalizar();
    }
    
    public boolean estaCargado() {
        return carga != null;
    }
    
    public void ActualizarPosicion() {
        posX += PASO * velocidadX;
        posY += PASO * velocidadY;
        double ancho = Entorno.getInstance().getAncho();
        double alto = Entorno.getInstance().getAlto();
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
            if (Entorno.getInstance().generador.nextDouble() < PROB_CHGT_DIRECTION) {
                velocidadX = Entorno.getInstance().generador.nextDouble() - 0.5;
                velocidadY = Entorno.getInstance().generador.nextDouble() - 0.5;
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
                    if (Entorno.getInstance().generador.nextDouble() < objetivo.ProbaDeTomar()) {
                        carga = Entorno.getInstance().TomarResiduo(objetivo);
                    }
                }
                else {
                    Entorno.getInstance().DepositarResiduo(objetivo);
                    carga = null;
                }
                ocupado = true;
            }
        }
        Normalizar();
    }
}
