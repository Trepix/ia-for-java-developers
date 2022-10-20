package io.trepix.ia.recogidaSelectiva;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// Entorno, que contiene los residuos y los agentes
public class Field {

    // Atributos
    protected Random generador;
    protected double ancho;
    protected double alto;
    protected ArrayList<Residuo> residuos;
    protected ArrayList<AgenteClasificacion> agentes;
    protected int numIteraciones = 0;
    private PropertyChangeSupport support;
    
    // Métodos
    public void AgregarChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
        
    public Field(StartConfig startConfig) {
        residuos = new ArrayList();
        agentes = new ArrayList();
        this.generador = startConfig.generator();
        support = new PropertyChangeSupport(this);
        Initializar(startConfig);
    }

    private void Initializar(StartConfig startConfig) {
        Initializar(
                startConfig.trashConfig().amountOfTrash(),
                startConfig.agentsNumber(),
                startConfig.size().width(), startConfig.size().height(),
                startConfig.trashConfig().types());
    }

    private void Initializar(int _numResiduos, int _numAgentes, double _ancho, double _alto, int _numTiposResiduos) {
        ancho = _ancho;
        alto = _alto;
        residuos.clear();
        for (int i = 0; i < _numResiduos; i++) {
            Residuo residuo = new Residuo(generador.nextDouble() * ancho, generador.nextDouble() * alto, generador.nextInt(_numTiposResiduos));
            residuos.add(residuo);
        }
        agentes.clear();
        for (int i = 0; i < _numAgentes; i++) {
            AgenteClasificacion agent = new AgenteClasificacion(generador.nextDouble() * ancho, generador.nextDouble() * alto, this);
            agentes.add(agent);
        }
    }
   
    public double getAncho() {
        return ancho;
    }
    public double getAlto() {
        return alto;
    }
    
    public void DepositarResiduo(Residuo d) {
        d.AumentarTamaño();        
    }
    
    public Residuo TomarResiduo(Residuo d) {
        if (d.tamaño == 1) {
            residuos.remove(d);
            return d;
        }
        else {
            d.ReducirTamaño();
            Residuo carga = new Residuo(d);
            return carga;
        }
    }
    
    public void Actualizar() {
        for (AgenteClasificacion agent : agentes) {
            agent.ActualizarDireccion(residuos);
            agent.ActualizarPosicion();
        }
        support.firePropertyChange("changed", numIteraciones, numIteraciones+1);

        numIteraciones++;
        if (numIteraciones % 500 == 0) {
            Collections.reverse(residuos);
        }
    }
}
