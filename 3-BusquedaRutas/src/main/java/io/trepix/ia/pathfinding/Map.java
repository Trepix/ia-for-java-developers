package io.trepix.ia.pathfinding;

import io.trepix.ia.pathfinding.structure.Arc;
import io.trepix.ia.pathfinding.structure.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Map implements Graph<Tile> {
    Tile[][] tiles;
    int numLineas;
    int numColumnas;
    Tile nodoInicio;
    Tile nodoLLegada;
    List<Tile> listaNodes = null;
    List<Arc<Tile>> listaArcos = null;

    // Constructor
    public Map(String _mapa, int _lineaInicio, int _columnaInicio, int _lineaLLegada, int _columnaLLegada) {
        // Creación de la tabla de baldosas
        String[] lineas = _mapa.split("\n");
        numLineas = lineas.length;
        numColumnas = lineas[0].length();
        tiles = new Tile[numLineas][];

        // Relleno
        for (int i = 0; i < numLineas; i++) {
            tiles[i] = new Tile[numColumnas];
            for (int j = 0; j < numColumnas; j++) {
                tiles[i][j] = Tile.TileFactory.create(lineas[i].charAt(j), i, j);
            }
        }

        // Inicio y llegada
        nodoInicio = tiles[_lineaInicio][_columnaInicio];
        nodoInicio.setDistanceFromBeginning(nodoInicio.movementCost());
        nodoLLegada = tiles[_lineaLLegada][_columnaLLegada];

        // Lista de los nodos y de las arcos
        nodes();
        arcs();
    }

    @Override
    public Tile startingNode() {
        return nodoInicio;
    }

    @Override
    public Tile endingNode() {
        return nodoLLegada;
    }

    @Override
    public List<Tile> nodes() {
        if (listaNodes == null) {
            listaNodes = new ArrayList();
            for (int i = 0; i < numLineas; i++) {
                listaNodes.addAll(Arrays.asList(tiles[i]));
            }
        }
        return listaNodes;
    }

    @Override
    public List<Tile> adjacentNodes(Tile origen) {
        // Iinicialización
        List<Tile> listaNodosSalientes = new ArrayList();
        int linea = origen.row();
        int columna = origen.column();

        // Vecino de la derecha
        if (columna - 1 >= 0 && tiles[linea][columna - 1].isAccessible()) {
            listaNodosSalientes.add(tiles[linea][columna - 1]);
        }

        // Vecino de la izquierda
        if (columna + 1 < numColumnas && tiles[linea][columna + 1].isAccessible()) {
            listaNodosSalientes.add(tiles[linea][columna + 1]);
        }

        // Vecino de arriba
        if (linea - 1 >= 0 && tiles[linea - 1][columna].isAccessible()) {
            listaNodosSalientes.add(tiles[linea - 1][columna]);
        }

        // Vecino de abajO
        if (linea + 1 < numLineas && tiles[linea + 1][columna].isAccessible()) {
            listaNodosSalientes.add(tiles[linea + 1][columna]);
        }
        return listaNodosSalientes;
    }

    @Override
    public int numberOfNodes() {
        return numLineas * numColumnas;
    }

    @Override
    public List<Arc<Tile>> arcsOf(Tile origen) {
        List<Arc<Tile>> listaArcosSalientes = new ArrayList();
        int linea = origen.row();
        int columna = origen.column();

        if (tiles[linea][columna].isAccessible()) {
            // Derecha
            if (columna - 1 >= 0 && tiles[linea][columna - 1].isAccessible()) {
                listaArcosSalientes.add(new Arc<>(tiles[linea][columna], tiles[linea][columna - 1], tiles[linea][columna - 1].movementCost()));
            }

            // Izquierda
            if (columna + 1 < numColumnas && tiles[linea][columna + 1].isAccessible()) {
                listaArcosSalientes.add(new Arc<>(tiles[linea][columna], tiles[linea][columna + 1], tiles[linea][columna + 1].movementCost()));
            }

            // Arriba
            if (linea - 1 >= 0 && tiles[linea - 1][columna].isAccessible()) {
                listaArcosSalientes.add(new Arc<>(tiles[linea][columna], tiles[linea - 1][columna], tiles[linea - 1][columna].movementCost()));
            }

            // Abajo
            if (linea + 1 < numLineas && tiles[linea + 1][columna].isAccessible()) {
                listaArcosSalientes.add(new Arc<>(tiles[linea][columna], tiles[linea + 1][columna], tiles[linea + 1][columna].movementCost()));
            }
        }
        return listaArcosSalientes;
    }

    @Override
    public List<Arc<Tile>> arcs() {
        if (listaArcos == null) {
            listaArcos = new ArrayList();

            // Recorrido de los nodos
            for (int linea = 0; linea < numLineas; linea++) {
                for (int columna = 0; columna < numColumnas; columna++) {
                    List<Arc<Tile>> arcs = arcsOf(tiles[linea][columna]);
                    listaArcos.addAll(arcs);
                }
            }
        }
        return listaArcos;
    }

    @Override
    public double cost(Tile start, Tile end) {
        return end.movementCost();
    }

    @Override
    public List<Tile> pathSteps() {
        Tile nodoActual = nodoLLegada;
        Tile nodoAnterior = nodoLLegada.getParent();

        LinkedList<Tile> path = new LinkedList<>();

        // Bucle sobre los nodos del camino
        while (nodoAnterior != null) {
            path.push(nodoActual);
            nodoActual = nodoAnterior;
            nodoAnterior = nodoActual.getParent();
        }
        path.push(nodoActual);
        return path;
    }

    @Override
    public void initializeEstimatedDistances() {
        for (int linea = 0; linea < numLineas; linea++) {
            for (int columna = 0; columna < numColumnas; columna++) {
                tiles[linea][columna].setEstimatedDistance(Math.abs(nodoLLegada.row() - linea) + Math.abs(nodoLLegada.column() - columna));
            }
        }
    }

    @Override
    public void clearPath() {
        // Eliminar las listas
        listaNodes = null;
        listaArcos = null;

        // Eliminar las distancias y precursores
        for (int linea = 0; linea < numLineas; linea++) {
            for (int columna = 0; columna < numColumnas; columna++) {
                tiles[linea][columna].setDistanceFromBeginning(Double.POSITIVE_INFINITY);
                tiles[linea][columna].setParent(null);
            }
        }

        // Nodo inicial
        nodoInicio.setDistanceFromBeginning(nodoInicio.movementCost());
    }

}
