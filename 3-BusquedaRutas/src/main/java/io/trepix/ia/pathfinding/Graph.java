package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Node;
import io.trepix.ia.pathfinding.structure.Tile;

import java.util.ArrayList;
import java.util.List;

// Interface que define los graficos
public interface Graph<T extends Node<T>> {
    // Información del grafico
    T NodoInicio();
    T NodoSalida();
    
    // Recuperacion de las nodos y de las arcos
    ArrayList<T> ListaNodos();
    ArrayList<T> ListaNodosAdyacentes(T origen);
    ArrayList<Arc> ListaArcos();
    ArrayList<Arc> ListaArcosSalientes(T origen);
    
    // Métodos herramientas
    int NumeroNodos();
    double Coste(T inicio, T llegada);

    List<Tile> ReconstruirCamino();
    void CalcularDistanciasEstimadas();
    void Eliminar();

}
