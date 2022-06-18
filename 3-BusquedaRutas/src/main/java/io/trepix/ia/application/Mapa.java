package io.trepix.ia.application;

import java.util.ArrayList;
import java.util.Arrays;
import io.trepix.ia.busquedaCaminos.Arc;
import io.trepix.ia.busquedaCaminos.Grafico;
import io.trepix.ia.busquedaCaminos.Nodo;

// Clase que representa el mapa a recorrer, heredando de Grafico
public class Mapa implements Grafico {

    // Atributos
    Baldosa[][] baldosas;
    int numLineas;
    int numColumnas;
    Baldosa nodoInicio;
    Baldosa nodoLLegada;
    ArrayList<Nodo> listaNodos = null;
    ArrayList<Arc> listaArcos = null;
    
    // Constructor
    public Mapa(String _mapa, int _lineaInicio, int _columnaInicio, int _lineaLLegada, int _columnaLLegada) {
        // Creación de la tabla de baldosas
        String[] lineas = _mapa.split("\n");
        numLineas = lineas.length;
        numColumnas = lineas[0].length();
        baldosas = new Baldosa[numLineas][];
        
        // Relleno
        for (int i = 0; i < numLineas; i++) {
            baldosas[i] = new Baldosa[numColumnas];
            for (int j = 0; j < numColumnas; j++) {
                TipoBaldosa type = ConvertidorTipoBaldosa.CharToType(lineas[i].charAt(j));
                baldosas[i][j] = new Baldosa(type, i, j);
            }
        }
        
        // Inicio y llegada
        nodoInicio = baldosas[_lineaInicio][_columnaInicio];
        nodoInicio.distanciaDesdeInicio = nodoInicio.Coste();
        nodoLLegada = baldosas[_lineaLLegada][_columnaLLegada];
        
        // Lista de los nodos y de las arcos
        ListaNodos();
        ListaArcos();
    }
    
    @Override
    public Nodo NodoInicio() {
        return nodoInicio;
    }

    @Override
    public Nodo NodoSalida() {
        return nodoLLegada;
    }

    @Override
    public ArrayList<Nodo> ListaNodos() {
        if (listaNodos == null) {
            listaNodos = new ArrayList();
            for (int i = 0; i < numLineas; i++) {
                listaNodos.addAll(Arrays.asList(baldosas[i]));
            }
        }
        return listaNodos;
    }

    @Override
    public ArrayList<Nodo> ListaNodosAdyacentes(Nodo origen) {
        // Iinicialización
        ArrayList<Nodo> listaNodosSalientes = new ArrayList();
        int linea = ((Baldosa)origen).linea;
        int columna = ((Baldosa)origen).columna;
        
        // Vecino de la derecha
        if (columna - 1 >= 0 && baldosas[linea][columna-1].Accessible()) {
            listaNodosSalientes.add(baldosas[linea][columna-1]);
        }
        
        // Vecino de la izquierda
        if (columna + 1 < numColumnas && baldosas[linea][columna+1].Accessible()) {
            listaNodosSalientes.add(baldosas[linea][columna+1]);
        }
        
        // Vecino de arriba
        if (linea - 1 >= 0 && baldosas[linea-1][columna].Accessible()) {
            listaNodosSalientes.add(baldosas[linea-1][columna]);
        }
        
        // Vecino de abajO
        if (linea + 1 < numLineas && baldosas[linea+1][columna].Accessible()) {
            listaNodosSalientes.add(baldosas[linea+1][columna]);
        }
        return listaNodosSalientes;
    }

    @Override
    public int NumeroNodos() {
        return numLineas * numColumnas;
    }

    @Override
    public ArrayList<Arc> ListaArcosSalientes(Nodo origen) {
        ArrayList<Arc> listaArcosSalientes = new ArrayList();
        int linea = ((Baldosa)origen).linea;
        int columna = ((Baldosa)origen).columna;
        
        if (baldosas[linea][columna].Accessible()) {
            // Derecha
            if (columna - 1 >= 0 && baldosas[linea][columna-1].Accessible()) {
                listaArcosSalientes.add(new Arc(baldosas[linea][columna], baldosas[linea][columna-1], baldosas[linea][columna-1].Coste()));
            }

            // Izquierda
            if (columna + 1 < numColumnas && baldosas[linea][columna+1].Accessible()) {
                listaArcosSalientes.add(new Arc(baldosas[linea][columna], baldosas[linea][columna+1], baldosas[linea][columna+1].Coste()));
            }

            // Arriba
            if (linea - 1 >= 0 && baldosas[linea-1][columna].Accessible()) {
                listaArcosSalientes.add(new Arc(baldosas[linea][columna], baldosas[linea-1][columna], baldosas[linea-1][columna].Coste()));
            }

            // Abajo
            if (linea + 1 < numLineas && baldosas[linea+1][columna].Accessible()) {
                listaArcosSalientes.add(new Arc(baldosas[linea][columna], baldosas[linea+1][columna], baldosas[linea+1][columna].Coste()));
            }
        }
        return listaArcosSalientes;
    }
    
    @Override
    public ArrayList<Arc> ListaArcos() {
        if(listaArcos == null) {
            listaArcos = new ArrayList();
            
            // Recorrido de los nodos
            for (int linea = 0; linea < numLineas; linea++) {
                for (int columna = 0; columna < numColumnas; columna++) {
                    ArrayList<Arc> arcs = ListaArcosSalientes(baldosas[linea][columna]);
                    listaArcos.addAll(arcs);
                }
            }
        }
        return listaArcos;
    }
    
    @Override
    public double Coste(Nodo inicio, Nodo llegada) {
        return ((Baldosa)llegada).Coste();
    }

    @Override
    public String ReconstruirCamino() {
        // Iinicialización
        String camino = "";
        Baldosa nodoActual = nodoLLegada;
        Baldosa nodoAnterior = (Baldosa) nodoLLegada.predecesor;
        
        // Bucle sobre los nodos del camino
        while (nodoAnterior != null) {
            camino = "-" + nodoActual.toString() + camino;
            nodoActual = nodoAnterior;
            nodoAnterior = (Baldosa) nodoActual.predecesor;
        }
        camino = nodoActual.toString() + camino;
        return camino;
    }

    @Override
    public void CalcularDistanciasEstimadas() {
        for (int linea = 0; linea < numLineas; linea++) {
            for (int columna = 0; columna < numColumnas; columna++) {
                baldosas[linea][columna].distanciaEstimada = Math.abs(nodoLLegada.linea - linea) + Math.abs(nodoLLegada.columna - columna);
            }
        }
    }

    @Override
    public void Eliminar() {
        // Eliminar las listas
        listaNodos = null;
        listaArcos = null;
        
        // Eliminar las distancias y precursores
        for (int linea = 0; linea < numLineas; linea++) {
            for (int columna = 0; columna < numColumnas; columna++) {
                baldosas[linea][columna].distanciaDesdeInicio = Double.POSITIVE_INFINITY;
                baldosas[linea][columna].predecesor = null;
            }
        }
        
        // Nodo inicial
        nodoInicio.distanciaDesdeInicio = nodoInicio.Coste();
    }
}
