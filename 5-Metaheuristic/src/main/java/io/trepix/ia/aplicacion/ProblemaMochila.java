package application;

import Metaheuristico.IProblema;
import Metaheuristico.ISolucion;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

// El problema de la mochila a resolver (maximizar el valor sin sobrepasar el peso)
public class ProblemaMochila implements IProblema {
    protected ArrayList<Caja> cajasDispo = null;
    public double pesosMax;
    public static Random generador = null;
    public final static int NUM_VECINOS = 30;
    
    // Problema del libro (12 cajas)
    public ProblemaMochila() {
        // Lista de las cajas
        cajasDispo = new ArrayList();
        cajasDispo.add(new Caja("A", 4, 15)); 
        cajasDispo.add(new Caja("B", 7, 15)); 
        cajasDispo.add(new Caja("C", 10, 20)); 
        cajasDispo.add(new Caja("D", 3, 10)); 
        cajasDispo.add(new Caja("E", 6, 11)); 
        cajasDispo.add(new Caja("F", 12, 16)); 
        cajasDispo.add(new Caja("G", 11, 12)); 
        cajasDispo.add(new Caja("H", 16, 22)); 
        cajasDispo.add(new Caja("I", 5, 12)); 
        cajasDispo.add(new Caja("J", 14, 21)); 
        cajasDispo.add(new Caja("K", 4, 10)); 
        cajasDispo.add(new Caja("L", 3, 7)); 

        pesosMax = 20;
        if (generador == null) {
            generador = new Random();
        }
    }
    
    // Constructor de problemas aleatorias
    public ProblemaMochila(int numCajas, double _pesosMax, double valorMax) {
        cajasDispo = new ArrayList();
        pesosMax = _pesosMax;
        if (generador == null) {
            generador = new Random();
        }
        for (int i = 0; i < numCajas; i++) {
            cajasDispo.add(new Caja(Integer.toString(i), generador.nextDouble() * pesosMax, generador.nextDouble() * valorMax));
        }
    }
    
    public ArrayList<Caja> Cajas() {
        ArrayList<Caja> copia = new ArrayList();
        copia.addAll(cajasDispo);
        return copia;
    }
    
    @Override
    public ISolucion SolucionAleatoria() {
        SolucionMochila solucion = new SolucionMochila();
        ArrayList<Caja> cajasPosibles = Cajas();
        double espacioDispo = pesosMax;
        // Se eliminan las demasiado pesadas
        EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        while(espacioDispo > 0 && !cajasPosibles.isEmpty()) {
            // Elección aleatoria de una caja
            int index = generador.nextInt(cajasPosibles.size());
            // Actualización de la solución
            Caja b = cajasPosibles.get(index);
            solucion.contenido.add(b);
            cajasPosibles.remove(index);
            espacioDispo -= b.pesos;
            // Actualización de la lista
            EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
        }
        return solucion;
    }
    
    public void EliminarDemasiadoPesadas(ArrayList<Caja> cajasPosibles, double espacioDispo) {
        Iterator<Caja> iterador = cajasPosibles.iterador();
        while (iterador.hasNext()) {
            Caja b = iterador.next();
            if (b.pesos > espacioDispo) {
                iterador.remove();
            }
        }
    }
    
    @Override
    public ISolucion MejorSolucion(ArrayList<ISolucion> soluciones) {
        if (!soluciones.isEmpty()) {
            ISolucion mejor = soluciones.get(0);
            for (ISolucion sol : soluciones) {
                if (sol.getValor() > mejor.getValor()) {
                    mejor = sol;
                }
            }
            return mejor;
        }
        else {
            return null;
        }
    }
    
    @Override
    public ArrayList<ISolucion> Vecindario(ISolucion solucionActual) {
        ArrayList<ISolucion> vecindario = new ArrayList();
        for (int i = 0; i < NUM_VECINOS; i++) {
            // Creación de un clon
            SolucionMochila solucion = new SolucionMochila((SolucionMochila) solucionActual);
            // se elimina un elemento
            int index = generador.nextInt(solucion.contenido.size());
            solucion.contenido.remove(index);
            // Cálculo de los pesos y de las cajas disponibles
            ArrayList<Caja> cajasPosibles = Cajas();
            double espacioDispo = pesosMax - solucion.getPeso();
            cajasPosibles.removeAll(solucion.contenido);
            EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
            // Se añaden las cajas
            while(espacioDispo > 0 && !cajasPosibles.isEmpty()) {
                // Elección aleatoria de una caja
                index = generador.nextInt(cajasPosibles.size());
                // Actualización de la solución
                Caja b = cajasPosibles.get(index);
                solucion.contenido.add(b);
                cajasPosibles.remove(index);
                espacioDispo -= b.pesos;
                // Actualización de la lista
                EliminarDemasiadoPesadas(cajasPosibles, espacioDispo);
            }
            vecindario.add(solucion);
        }
        return vecindario;
    }
}
