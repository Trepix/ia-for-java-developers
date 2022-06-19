package io.trepix.ia.busquedaCaminos;

import io.trepix.ia.busquedaCaminos.structure.Arc;
import io.trepix.ia.busquedaCaminos.structure.Node;

import java.util.ArrayList;

// Algoritmo A*
public class AStar extends Algoritmo {

    // Constructor
    public AStar() {
        super("A*");
    }
    
    // Métodos principal
    @Override
    protected Grafico Run(Grafico grafico) {
        // Iinicialización
        grafico.CalcularDistanciasEstimadas();
        ArrayList<Node> listaNodes = grafico.ListaNodos();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(listaNodes.size() != 0 && !salidaEncontrada) {
            // Búsqueda del nodo con la distancia más baja
            Node nodeActual = listaNodes.get(0);
            for (Node node : listaNodes) {
                if (node.getDistanceFromBeginning() + node.getEstimatedDistance() < nodeActual.getDistanceFromBeginning() + nodeActual.getEstimatedDistance()) {
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
                    if (arc.origin().getDistanceFromBeginning() + arc.cost() < arc.destination().getDistanceFromBeginning()) {
                        arc.destination().setDistanceFromBeginning(arc.origin().getDistanceFromBeginning() + arc.cost());
                        arc.destination().setParent(arc.origin());
                    }
                }
                
                listaNodes.remove(nodeActual);
            }
        }

        return grafico;
    }
}
