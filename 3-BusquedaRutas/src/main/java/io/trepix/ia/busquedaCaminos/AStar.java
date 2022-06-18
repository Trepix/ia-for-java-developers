package io.trepix.ia.busquedaCaminos;

import java.util.ArrayList;

// Algoritmo A*
public class AStar extends Algoritmo {

    // Constructor
    public AStar(Grafico _grafico, IHM _ihm) {
        super(_grafico, _ihm);
    }
    
    // Métodos principal
    @Override
    protected void Run() {
        // Iinicialización
        grafico.CalcularDistanciasEstimadas();
        ArrayList<Node> listaNodes = grafico.ListaNodos();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(listaNodes.size() != 0 && !salidaEncontrada) {
            // Búsqueda del nodo con la distancia más baja
            Node nodeActual = listaNodes.get(0);
            for (Node node : listaNodes) {
                if (node.distanceFromBeginning + node.estimatedDistance < nodeActual.distanceFromBeginning + nodeActual.estimatedDistance) {
                    nodeActual = node;
                }
            }
            
            if (nodeActual.equals(grafico.NodoSalida())) {
                // Encontrada la salida
                salidaEncontrada = true;
            }
            else {
                // Se aplican los arcos salientes de este nodo
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
