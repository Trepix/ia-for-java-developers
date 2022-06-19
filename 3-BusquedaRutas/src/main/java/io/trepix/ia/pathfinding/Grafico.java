package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;
import io.trepix.ia.pathfinding.structure.Tile;

import java.util.ArrayList;
import java.util.List;

// Interface que define los graficos
public interface Grafico {
    // Información del grafico
    Node NodoInicio();
    Node NodoSalida();
    
    // Recuperacion de las nodos y de las arcos
    ArrayList<Node> ListaNodos();
    ArrayList<Node> ListaNodosAdyacentes(Node origen);
    ArrayList<Arc> ListaArcos();
    ArrayList<Arc> ListaArcosSalientes(Node origen);
    
    // Métodos herramientas
    int NumeroNodos();
    double Coste(Node inicio, Node llegada);

    List<Tile> ReconstruirCamino();
    void CalcularDistanciasEstimadas();
    void Eliminar();

}
