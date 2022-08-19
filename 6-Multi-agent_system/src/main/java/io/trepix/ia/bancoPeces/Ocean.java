package io.trepix.ia.bancoPeces;

import io.trepix.ia.Size;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static java.util.Arrays.asList;

// El oceano donde nadan los peces
public class Ocean {
    // Atributos
    protected Fish[] peces;
    protected ArrayList<Obstacle> obstaculos;
    protected Random generador;
    private PropertyChangeSupport support;
    private int contador;

    private final Size size;

    public void AgregarChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public Ocean(Size size, StartConfig startConfig) {
        support = new PropertyChangeSupport(this);
        contador = 0;
        this.generador = startConfig.generator();
        this.size = size;
        obstaculos = new ArrayList();        
        peces = new Fish[startConfig.fishNumber()];
        for (int i = 0; i < startConfig.fishNumber(); i++) {
            peces[i] = new Fish(generador.nextDouble() * size.width(), generador.nextDouble() * size.height(), generador.nextDouble() * 2 * Math.PI);
        }

    }

    public List<Fish> fishes() {
        return asList(peces);
    }

    public List<Obstacle> obstacles() {
        return obstaculos;
    }

    public void addObstacleAt(Position position) {
        obstaculos.add(new Obstacle(position));
    }
    
    protected void ActualizarObstaculos() {
        for(Obstacle obstaculo : obstaculos) {
            obstaculo.evolve();
        }
        obstaculos.removeIf(o -> o.isDead());
    }
    
    protected void ActualizarPeces() {
        for (Fish p : peces) {
            p.Actualizar(peces, obstaculos, size.width(), size.height());
        }
    }
    
    public void ActualizarOceano() {
        ActualizarObstaculos();
        ActualizarPeces();
        support.firePropertyChange("changed", this.contador, this.contador+1);
        this.contador++;
    }
}
