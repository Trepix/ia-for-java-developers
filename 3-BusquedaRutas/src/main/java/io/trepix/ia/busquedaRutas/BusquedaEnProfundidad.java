package busquedaCaminos;

import java.util.ArrayList;
import java.util.Stack;

// Algoritmo de búsqueda en profundidad
public class BúsquedaEnProfundidad extends Algoritmo {

    // Constructor
    public BúsquedaEnProfundidad(Grafico _grafico, IHM _ihm) {
        super(_grafico,_ihm);
    }
    
    // Métodos de resolución
    @Override
    protected void Run() {
        // Creación de la lista de  nodos no visitados y de la pila
        ArrayList<Nodo> nodosNoVisitados = grafico.ListaNodos();
        Stack<Nodo> nodosAVisitador = new Stack();
        nodosAVisitador.push(grafico.NodoInicio());
        nodosNoVisitados.remove(grafico.NodoInicio());
        
        // Iinicialización de la salida
        Nodo nodoSalida = grafico.NodoSalida();
        boolean salidaEncontrada = false;
        
        // Bucle principal
        while(!salidaEncontrada && nodosAVisitador.size() != 0) {
            Nodo nodoActual = nodosAVisitador.pop();
            if (nodoActual.equals(nodoSalida)) {
                // Se terina el algoritmo
                salidaEncontrada = true;
            }
            else {
                // Se añaden los vecinos no visitados todavía
                for (Nodo n : grafico.ListaNodosAdyacentes(nodoActual)) {
                    if (nodosNoVisitados.contains(n)) {
                        nodosNoVisitados.remove(n);
                        n.predecesor = nodoActual;
                        n.distanciaDesdeInicio = nodoActual.distanciaDesdeInicio + grafico.Cout(nodoActual, n);
                        nodosAVisitador.push(n);
                    }
                }
            }
        }
    }
}
