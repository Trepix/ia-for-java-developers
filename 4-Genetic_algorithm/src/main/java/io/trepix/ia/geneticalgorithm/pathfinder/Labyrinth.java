package io.trepix.ia.geneticalgorithm.pathfinder;

import io.trepix.ia.geneticalgorithm.Gene;
import java.util.ArrayList;
import java.util.List;

// Representa un laberinto con los posibles pasos, la entrada y la salida
public class Labyrinth {
    // Información del laberinto
    private static ArrayList<Box[]> caminos;
    private static Box entrada;
    private static Box salida;
    
    // Direcciones
    public enum Direccion { Arriba, Abajo, Izquierda, Derecha};
    
    // Diseño de los laberintos
    public static String Map1 = "*--*--*--*--*\n" +
                                "E           |\n" + 
                                "*  *  *--*--*\n" + 
                                "|  |  |     |\n" + 
                                "*  *--*  *  *\n" + 
                                "|        |  |\n" + 
                                "*  *--*--*  *\n" + 
                                "|        |  S\n" + 
                                "*--*--*--*--*"; 
    public static String Map2 = "*--*--*--*--*--*--*\n" +
                                "E        |  |     |\n" + 
                                "*--*--*  *  *  *--*\n" + 
                                "|     |     |     |\n" + 
                                "*  *  *  *  *  *  *\n" + 
                                "|  |  |  |     |  |\n" + 
                                "*--*  *  *--*--*  *\n" + 
                                "|     |  |     |  |\n" + 
                                "*  *--*--*  *  *  *\n" + 
                                "|  |        |  |  |\n" + 
                                "*  *  *  *--*  *  *\n" + 
                                "|     |     |     S\n" + 
                                "*--*--*--*--*--*--*";

    public Labyrinth(String map) {
        Init(map);
    }

    private static void Init(String s) {
        caminos = new ArrayList();
        
        // Nos separramos y después se trata cada línea
        String[] lineas = s.split("\n");
        int numLineas = 0;
        for (String linea : lineas) {
            if (numLineas % 2 != 0) {
                // Número impar, línea de pasillo
                int index = linea.indexOf("E");
                if (index != -1) {
                    // Tenemos una entrada en ese pasillo
                    if (index == linea.length() - 1) {
                        index --;
                    }
                    entrada = new Box(numLineas/2, index /3);
                }
                index = linea.indexOf("S");
                if (index != -1) {
                    // Salida en el pasillo
                    if (index == linea.length() - 1) {
                        index--;
                    }
                    salida = new Box(numLineas/2, index/3);
                }
                // recorremos el pasillo para crear los caminos horizontales
                for (int columna = 0; columna < linea.length() / 3; columna++) {
                    String casillaStr = linea.substring(columna*3, columna*3 + 3);
                    if (!casillaStr.contains("|") && !casillaStr.contains("E") && !casillaStr.contains("S")) {
                        // Paso abierto, se añade
                        caminos.add(new Box[]{new Box(numLineas/2, columna-1), new Box(numLineas/2, columna)});
                    }
                }
            }
            else {
                // Línea par : en los muros
                String [] casillas = linea.substring(1).split("\\*");
                int columna = 0;
                for (String bloc : casillas) {
                    if (bloc.equals("  ")) {
                        // Paso abierto, se añade
                        caminos.add(new Box[] {new Box(numLineas/2 - 1, columna), new Box(numLineas/2, columna)});
                    }
                    columna++;
                }
            }
            numLineas++;
        }
    }
    
    // Indica si un movimiento entre dos casillas es posible
    private static boolean esPosible(Box pos1, Box pos2) {
        for (Box[] camino : caminos) {
            if ((camino[0].equals(pos1) && camino[1].equals(pos2)) || ((camino[0].equals(pos2) && camino[1].equals(pos1)))) {
                return true;
            }
        }
        return false;
    }
    
    // Indica si una casilla es un cruce
    private static boolean EsCruce(Box pos) {
        int numCaminos = 0;
        for (Box[] camino : caminos) {
            if (camino[0].equals(pos) || camino[1].equals(pos)) {
                numCaminos++;
            }
        }
        return numCaminos > 2;
    }
    
    // Mira si el movimiento es posible
    static void Mover(Box inicio, int deplI, int deplJ) {
        boolean finMovimiento = false;
        while(esPosible(inicio, new Box(inicio.getI() + deplI, inicio.getJ() + deplJ)) && !finMovimiento) {
            inicio.setI(inicio.getI() + deplI);
            inicio.setJ(inicio.getJ() + deplJ);
            finMovimiento = EsCruce(inicio) || inicio.equals(salida);
        }
        finMovimiento = false;
    }
    
    // Mueve un individuo en el laberinto para evaluarlo
    double Evaluar(List<Gene> genoma) {
        Box boxActual = new Box(entrada.getI(), entrada.getJ());
        boolean finMovimiento = false;
        for(Gene g : genoma) {
            switch (((DirectionUntilNextIntersection)g).dirección) {
                case Abajo :
                    Mover(boxActual, 1, 0);
                    break;
                case Arriba :
                    Mover(boxActual, -1, 0);
                    break;
                case Derecha :
                    Mover(boxActual, 0, 1);
                    break;
                case Izquierda :
                    Mover(boxActual, 0, -1);
                    break;
            }
            if (boxActual.equals(salida)) {
                break;
            }
        }
        // Cálculo del fitness : distancia de Manhattan
        int distancia = Math.abs(salida.getI() - boxActual.getI()) + Math.abs(salida.getJ() - boxActual.getJ());
        return distancia;
    }
}
