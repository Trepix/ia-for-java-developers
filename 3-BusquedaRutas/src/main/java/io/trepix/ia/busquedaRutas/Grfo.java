package busquedaCaminos;

import java.util.ArrayList;

// Interface que define los graficos
public interface Grafico {
    // Información del grafico
    Nodo NodoInicio();
    Nodo NodoSalida();
    
    // Recuperacion de las nodos y de las arcos
    ArrayList<Nodo> ListaNodos();
    ArrayList<Nodo> ListaNodosAdyacentes(Nodo origen);
    ArrayList<Arc> ListaArcos();
    ArrayList<Arc> ListaArcosSalientes(Nodo origen);
    
    // Métodos herramientas
    int NumeroNodos();
    double Coste(Nodo inicio, Nodo llegada);
    String ReconstruirCamino();
    void CalcularDistanciasEstimadas();
    void Eliminar();
}
