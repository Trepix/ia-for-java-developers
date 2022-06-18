package io.trepix.ia.busquedaCaminos;

import java.util.ArrayList;

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
    String ReconstruirCamino();
    void CalcularDistanciasEstimadas();
    void Eliminar();
}
