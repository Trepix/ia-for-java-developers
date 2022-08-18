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
    protected double ancho;
    protected double alto;
    private PropertyChangeSupport support;
    private int contador;
    
    // Métodos
    public void AgregarChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
 
    public void EliminarPropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }    
    
    public Ocean(Size size, int _numPeces) {
        support = new PropertyChangeSupport(this);
        contador = 0;
        ancho = size.width();
        alto = size.height();
        generador = new Random();
        obstaculos = new ArrayList();        
        peces = new Fish[_numPeces];
        for (int i = 0; i < _numPeces; i++) {
            peces[i] = new Fish(generador.nextDouble() * ancho, generador.nextDouble() * alto, generador.nextDouble() * 2 * Math.PI);
        }
    }

    public List<Fish> fishes() {
        return asList(peces);
    }

    public List<Obstacle> obstacles() {
        return obstaculos;
    }

    public void AgregarObstaculo(Position position) {
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
            p.Actualizar(peces, obstaculos, ancho, alto);
        }
    }
    
    public void ActualizarOceano() {
        ActualizarObstaculos();
        ActualizarPeces();
        support.firePropertyChange("changed", this.contador, this.contador+1);
        this.contador++;
    }
}
