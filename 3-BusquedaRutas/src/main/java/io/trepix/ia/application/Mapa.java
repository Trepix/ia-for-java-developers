package io.trepix.ia.application;

import java.util.ArrayList;
import java.util.Arrays;
import io.trepix.ia.busquedaCaminos.Arc;
import io.trepix.ia.busquedaCaminos.Grafico;
import io.trepix.ia.busquedaCaminos.Node;

// Clase que representa el mapa a recorrer, heredando de Grafico
public class Mapa implements Grafico {

    // Atributos
    Tile[][] tiles;
    int numLineas;
    int numColumnas;
    Tile nodoInicio;
    Tile nodoLLegada;
    ArrayList<Node> listaNodes = null;
    ArrayList<Arc> listaArcos = null;
    
    // Constructor
    public Mapa(String _mapa, int _lineaInicio, int _columnaInicio, int _lineaLLegada, int _columnaLLegada) {
        // Creación de la tabla de baldosas
        String[] lineas = _mapa.split("\n");
        numLineas = lineas.length;
        numColumnas = lineas[0].length();
        tiles = new Tile[numLineas][];
        
        // Relleno
        for (int i = 0; i < numLineas; i++) {
            tiles[i] = new Tile[numColumnas];
            for (int j = 0; j < numColumnas; j++) {
                TileType type = ConvertidorTipoBaldosa.CharToType(lineas[i].charAt(j));
                tiles[i][j] = new Tile(type, i, j);
            }
        }
        
        // Inicio y llegada
        nodoInicio = tiles[_lineaInicio][_columnaInicio];
        nodoInicio.distanceFromBeginning = nodoInicio.Coste();
        nodoLLegada = tiles[_lineaLLegada][_columnaLLegada];
        
        // Lista de los nodos y de las arcos
        ListaNodos();
        ListaArcos();
    }
    
    @Override
    public Node NodoInicio() {
        return nodoInicio;
    }

    @Override
    public Node NodoSalida() {
        return nodoLLegada;
    }

    @Override
    public ArrayList<Node> ListaNodos() {
        if (listaNodes == null) {
            listaNodes = new ArrayList();
            for (int i = 0; i < numLineas; i++) {
                listaNodes.addAll(Arrays.asList(tiles[i]));
            }
        }
        return listaNodes;
    }

    @Override
    public ArrayList<Node> ListaNodosAdyacentes(Node origen) {
        // Iinicialización
        ArrayList<Node> listaNodosSalientes = new ArrayList();
        int linea = ((Tile)origen).row;
        int columna = ((Tile)origen).column;
        
        // Vecino de la derecha
        if (columna - 1 >= 0 && tiles[linea][columna-1].isAccessible()) {
            listaNodosSalientes.add(tiles[linea][columna-1]);
        }
        
        // Vecino de la izquierda
        if (columna + 1 < numColumnas && tiles[linea][columna+1].isAccessible()) {
            listaNodosSalientes.add(tiles[linea][columna+1]);
        }
        
        // Vecino de arriba
        if (linea - 1 >= 0 && tiles[linea-1][columna].isAccessible()) {
            listaNodosSalientes.add(tiles[linea-1][columna]);
        }
        
        // Vecino de abajO
        if (linea + 1 < numLineas && tiles[linea+1][columna].isAccessible()) {
            listaNodosSalientes.add(tiles[linea+1][columna]);
        }
        return listaNodosSalientes;
    }

    @Override
    public int NumeroNodos() {
        return numLineas * numColumnas;
    }

    @Override
    public ArrayList<Arc> ListaArcosSalientes(Node origen) {
        ArrayList<Arc> listaArcosSalientes = new ArrayList();
        int linea = ((Tile)origen).row;
        int columna = ((Tile)origen).column;
        
        if (tiles[linea][columna].isAccessible()) {
            // Derecha
            if (columna - 1 >= 0 && tiles[linea][columna-1].isAccessible()) {
                listaArcosSalientes.add(new Arc(tiles[linea][columna], tiles[linea][columna-1], tiles[linea][columna-1].Coste()));
            }

            // Izquierda
            if (columna + 1 < numColumnas && tiles[linea][columna+1].isAccessible()) {
                listaArcosSalientes.add(new Arc(tiles[linea][columna], tiles[linea][columna+1], tiles[linea][columna+1].Coste()));
            }

            // Arriba
            if (linea - 1 >= 0 && tiles[linea-1][columna].isAccessible()) {
                listaArcosSalientes.add(new Arc(tiles[linea][columna], tiles[linea-1][columna], tiles[linea-1][columna].Coste()));
            }

            // Abajo
            if (linea + 1 < numLineas && tiles[linea+1][columna].isAccessible()) {
                listaArcosSalientes.add(new Arc(tiles[linea][columna], tiles[linea+1][columna], tiles[linea+1][columna].Coste()));
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
                    ArrayList<Arc> arcs = ListaArcosSalientes(tiles[linea][columna]);
                    listaArcos.addAll(arcs);
                }
            }
        }
        return listaArcos;
    }
    
    @Override
    public double Coste(Node inicio, Node llegada) {
        return ((Tile)llegada).Coste();
    }

    @Override
    public String ReconstruirCamino() {
        // Iinicialización
        String camino = "";
        Tile nodoActual = nodoLLegada;
        Tile nodoAnterior = (Tile) nodoLLegada.parent;
        
        // Bucle sobre los nodos del camino
        while (nodoAnterior != null) {
            camino = "-" + nodoActual.toString() + camino;
            nodoActual = nodoAnterior;
            nodoAnterior = (Tile) nodoActual.parent;
        }
        camino = nodoActual.toString() + camino;
        return camino;
    }

    @Override
    public void CalcularDistanciasEstimadas() {
        for (int linea = 0; linea < numLineas; linea++) {
            for (int columna = 0; columna < numColumnas; columna++) {
                tiles[linea][columna].estimatedDistance = Math.abs(nodoLLegada.row - linea) + Math.abs(nodoLLegada.column - columna);
            }
        }
    }

    @Override
    public void Eliminar() {
        // Eliminar las listas
        listaNodes = null;
        listaArcos = null;
        
        // Eliminar las distancias y precursores
        for (int linea = 0; linea < numLineas; linea++) {
            for (int columna = 0; columna < numColumnas; columna++) {
                tiles[linea][columna].distanceFromBeginning = Double.POSITIVE_INFINITY;
                tiles[linea][columna].parent = null;
            }
        }
        
        // Nodo inicial
        nodoInicio.distanceFromBeginning = nodoInicio.Coste();
    }
}
