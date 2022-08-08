package io.trepix.ia.recogidaSelectiva;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// Entorno, que contiene los residuos y los agentes
public class Entorno {
    // Gestión del singleton
    private static Entorno instancia;
    
    public static Entorno getInstance() {
        if (instancia == null) {
            instancia = new Entorno();
        }
        return instancia;
    }
    
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
        
    private Entorno() {
        residuos = new ArrayList();
        agentes = new ArrayList();
        generador = new Random();
        support = new PropertyChangeSupport(this);
    }
    
    public void Initializar(int _numResiduos, int _numAgentes, double _ancho, double _alto, int _numTiposResiduos) {
        ancho = _ancho;
        alto = _alto;
        residuos.clear();
        for (int i = 0; i < _numResiduos; i++) {
            Residuo residuo = new Residuo(generador.nextDouble() * ancho, generador.nextDouble() * alto, generador.nextInt(_numTiposResiduos));
            residuos.add(residuo);
        }
        agentes.clear();
        for (int i = 0; i < _numAgentes; i++) {
            AgenteClasificacion agent = new AgenteClasificacion(generador.nextDouble() * ancho, generador.nextDouble() * alto);
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
