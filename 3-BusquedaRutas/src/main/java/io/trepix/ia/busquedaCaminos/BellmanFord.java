package io.trepix.ia.busquedaCaminos;

import java.util.ArrayList;

// Algoritmo de Bellman-Ford
public class BellmanFord extends Algoritmo {
    // Constructor
    public BellmanFord(Grafico _grafico, IHM _ihm) {
        super(_grafico, _ihm);
    }

    // Métodos de resolución
    @Override
    protected void Run() {
        // Iinicialización
        boolean distanciaCambiada = true;
        int i = 0;
        ArrayList<Arc> listaArcos = grafico.ListaArcos();
        
        // Bucle principal
        int numBucleMax = grafico.NumeroNodos() - 1;
        while (i < numBucleMax && distanciaCambiada) {
            distanciaCambiada = false;
            for (Arc arc : listaArcos) {
                if (arc.origen.distanceFromBeginning + arc.cout < arc.destino.distanceFromBeginning) {
                    // Encontrado un camino más corto
                    arc.destino.distanceFromBeginning = arc.origen.distanceFromBeginning + arc.cout;
                    arc.destino.parent = arc.origen;
                    distanciaCambiada = true;
                }
            }
            i++;
        }
        
        // Prueba si el bucle es negativo
        for (Arc arc : listaArcos) {
            if (arc.origen.distanceFromBeginning + arc.cout < arc.destino.distanceFromBeginning) {
                System.err.println("Bucle negativo - sin camino más corto");
            }
        }
    }
}
