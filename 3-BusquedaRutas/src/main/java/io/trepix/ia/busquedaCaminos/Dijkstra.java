package io.trepix.ia.busquedaCaminos;

import java.util.ArrayList;

// Algoritmo de Dijkstra
public class Dijkstra extends Algoritmo {

    // Constructor
    public Dijkstra(Grafico _grafico, IHM _ihm) {
        super(_grafico, _ihm);
    }
    
    // Métodos principal
    @Override
    protected void Run() {
        // Iinicialización
        ArrayList<Node> listaNodes = grafico.ListaNodos();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(listaNodes.size() != 0 && !salidaEncontrada) {
            // Búsqueda del nodo con la distancia la más baja
            Node nodeActual = listaNodes.get(0);
            for (Node node : listaNodes) {
                if (node.distanceFromBeginning < nodeActual.distanceFromBeginning) {
                    nodeActual = node;
                }
            }
            
            if (nodeActual.equals(grafico.NodoSalida())) {
                salidaEncontrada = true;
            }
            else {
                // Se aplica los arco salientes de este nodo
                ArrayList<Arc> arcosSalientes = grafico.ListaArcosSalientes(nodeActual);
                
                for (Arc arc : arcosSalientes) {
                    if (arc.origen.distanceFromBeginning + arc.cout < arc.destino.distanceFromBeginning) {
                        arc.destino.distanceFromBeginning = arc.origen.distanceFromBeginning + arc.cout;
                        arc.destino.parent = arc.origen;
                    }
                }
                
                listaNodes.remove(nodeActual);
            }
        }
    }
    
}
