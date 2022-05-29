package algoritmogenetico.laberinto;

import algoritmogenetico.IGen;
import java.util.ArrayList;

// Representa un laberinto con los posibles pasos, la entrada y la salida
public class Laberinto {
    // Información del laberinto
    private static ArrayList<Casilla[]> caminos;
    private static Casilla entrada;
    private static Casilla salida;
    
    // Direcciones
    public enum Direccion { Arriba, Abajo, Izquierda, Derecha};
    
    // Diseño de los laberintos
    public static String Lab1 = "*--*--*--*--*\n" + 
                                "E           |\n" + 
                                "*  *  *--*--*\n" + 
                                "|  |  |     |\n" + 
                                "*  *--*  *  *\n" + 
                                "|        |  |\n" + 
                                "*  *--*--*  *\n" + 
                                "|        |  S\n" + 
                                "*--*--*--*--*"; 
    public static String Lab2 = "*--*--*--*--*--*--*\n" + 
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

    public static void Init(String s) {
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
                    entrada = new Case(numLineas/2, index /3);
                }
                index = linea.indexOf("S");
                if (index != -1) {
                    // Salida en el pasillo
                    if (index == linea.length() - 1) {
                        index--;
                    }
                    salida = new Case(numLineas/2, index/3);
                }
                // recorremos el pasillo para crear los caminos horizontales
                for (int columna = 0; columna < linea.length() / 3; columna++) {
                    String casillaStr = linea.substring(columna*3, columna*3 + 3);
                    if (!casillaStr.contains("|") && !casillaStr.contains("E") && !casillaStr.contains("S")) {
                        // Paso abierto, se añade
                        caminos.add(new Case[]{new Case(numLineas/2, columna-1), new Case(numLineas/2, columna)});
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
                        caminos.add(new Case[] {new Case(numLineas/2 - 1, columna), new Case(numLineas/2, columna)});
                    }
                    columna++;
                }
            }
            numLineas++;
        }
    }
    
    // Indica si un movimiento entre dos casillas es posible
    private static boolean esPosible(Casilla pos1, Casilla pos2) {
        for (Casilla[] camino : caminos) {
            if ((camino[0].equals(pos1) && camino[1].equals(pos2)) || ((camino[0].equals(pos2) && camino[1].equals(pos1)))) {
                return true;
            }
        }
        return false;
    }
    
    // Indica si una casilla es un cruce
    private static boolean EsCruce(Casilla pos) {
        int numCaminos = 0;
        for (Casilla[] camino : caminos) {
            if (camino[0].equals(pos) || camino[1].equals(pos)) {
                numCaminos++;
            }
        }
        return numCaminos > 2;
    }
    
    // Mira si el movimiento es posible
    static void Mover(Casilla inicio, int deplI, int deplJ) {
        boolean finMovimiento = false;
        while(esPosible(inicio, new Case(inicio.i + deplI, inicio.j + deplJ)) && !finMovimiento) {
            inicio.i += deplI;
            inicio.j += deplJ;
            finMovimiento = EsCruce(inicio) || inicio.equals(salida);
        }
        finMovimiento = false;
    }
    
    // Mueve un individuo en el laberinto para evaluarlo
    static double Evaluar(ArrayList<IGen> genoma) { 
        Casilla casillaActual = new Case(entrada.i, entrada.j);
        boolean finMovimiento = false;
        for(IGen g : genoma) {
            switch (((LabGen)g).dirección) {
                casilla Abajo :
                    Mover(casillaActual, 1, 0);
                    break;
                casilla Arriba :
                    Mover(casillaActual, -1, 0);
                    break;
                casilla Derecha :
                    Mover(casillaActual, 0, 1);
                    break;
                casilla Izquierda :
                    Mover(casillaActual, 0, -1);
                    break;
            }
            if (casillaActual.equals(salida)) {
                break;
            }
        }
        // Cálculo del fitness : distancia de Manhattan
        int distancia = Math.abs(salida.i - casillaActual.i) + Math.abs(salida.j - casillaActual.j);
        return distancia;
    }
}
