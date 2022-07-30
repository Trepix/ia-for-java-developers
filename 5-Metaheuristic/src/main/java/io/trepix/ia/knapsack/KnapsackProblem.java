package io.trepix.ia.knapsack;

import io.trepix.ia.metaheuristics.Problem;
import io.trepix.ia.metaheuristics.Solution;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

// El problema de la mochila a resolver (maximizar el valor sin sobrepasar el peso)
public class KnapsackProblem implements Problem {
    protected List<Caja> cajasDispo;
    public double pesosMax;
    public static Random generador = null;
    private final int NUM_VECINOS;

    public KnapsackProblem(List<Caja> items, Configuration configuration) {
        this.pesosMax = configuration.maximumKnapsackWeight();
        this.cajasDispo = items;
        generador = configuration.random();
        this.NUM_VECINOS = configuration.neighboursNumber();
    }

    public ArrayList<Caja> Cajas() {
        ArrayList<Caja> copia = new ArrayList();
        copia.addAll(cajasDispo);
        return copia;
    }
    
    @Override
    public Solution SolucionAleatoria() {
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
        Iterator<Caja> iterador = cajasPosibles.iterator();
        while (iterador.hasNext()) {
            Caja b = iterador.next();
            if (b.pesos > espacioDispo) {
                iterador.remove();
            }
        }
    }
    
    @Override
    public Solution MejorSolucion(List<Solution> soluciones) {
        if (!soluciones.isEmpty()) {
            Solution mejor = soluciones.get(0);
            for (Solution sol : soluciones) {
                if (sol.value() > mejor.value()) {
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
    public List<Solution> Vecindario(Solution solucionActual) {
        ArrayList<Solution> vecindario = new ArrayList();
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