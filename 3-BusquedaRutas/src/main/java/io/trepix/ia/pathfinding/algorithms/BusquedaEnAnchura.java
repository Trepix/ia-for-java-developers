package io.trepix.ia.pathfinding.algorithms;

import io.trepix.ia.pathfinding.PathFindingAlgorithm;
import io.trepix.ia.pathfinding.Grafico;
import io.trepix.ia.pathfinding.structure.Node;

import java.util.ArrayList;
import java.util.LinkedList;

// Algoritmo de búsqueda en ancho
public class BusquedaEnAnchura extends PathFindingAlgorithm {

    // Constructor
    public BusquedaEnAnchura() {
        super("Breadth (BFS)");
    }
    
    // Métodos de resolución
    @Override
    protected Path execute(Grafico grafico) {
        // Creación de la lista de nodos no visitados y de la pila
        ArrayList<Node> nodosNoVisitados = grafico.ListaNodos();
        LinkedList<Node> nodosAVisitador = new LinkedList();
        nodosAVisitador.add(grafico.NodoInicio());
        nodosNoVisitados.remove(grafico.NodoInicio());
        
        // Iinicialización de la salida
        Node nodeSalida = grafico.NodoSalida();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(!salidaEncontrada && nodosAVisitador.size() != 0) {
            Node nodeActual = nodosAVisitador.removeFirst();
            if (nodeActual.equals(nodeSalida)) {
                // Fin del algoritmo
                salidaEncontrada = true;
            }
            else {
                // Se añaden los vecinos no visitados todavóa
                for (Node n : grafico.ListaNodosAdyacentes(nodeActual)) {
                    if (nodosNoVisitados.contains(n)) {
                        nodosNoVisitados.remove(n);
                        n.setParent(nodeActual);
                        n.setDistanceFromBeginning(nodeActual.getDistanceFromBeginning() + grafico.Coste(nodeActual, n));
                        nodosAVisitador.add(n);
                    }
                }
            }
        }

        return new Path(grafico.ReconstruirCamino(), grafico.NodoSalida().getEstimatedDistance());
    }
}
